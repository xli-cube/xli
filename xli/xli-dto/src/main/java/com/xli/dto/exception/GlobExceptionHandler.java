package com.xli.dto.exception;

import com.xli.dto.result.ResultVO;
import com.xli.dto.result.status.Status;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobExceptionHandler {

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        List<String> defaultMessages = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        log.error(String.join(",", defaultMessages), e.getMessage());
        return new ResultVO<>(Status.ERR_201, String.join(",", defaultMessages));
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ResultVO<String> handleValidationException(ValidationException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResultVO<>(Status.ERR_201, e.getCause().getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultVO<String> handlerNoFoundException(NoHandlerFoundException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResultVO<>(Status.FAILED, "操作失败");
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResultVO<String> handleSQLSyntaxErrorException(SQLSyntaxErrorException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResultVO<>(Status.FAILED, "操作失败");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVO<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResultVO<>(Status.FAILED, "操作失败");
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<String> handleException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ResultVO<>(Status.FAILED, "操作失败");
    }
}
