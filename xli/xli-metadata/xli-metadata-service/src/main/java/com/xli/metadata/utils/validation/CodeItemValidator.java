package com.xli.metadata.utils.validation;

import cn.hutool.core.util.StrUtil;
import com.xli.metadata.codeitems.entity.CodeItems;
import com.xli.metadata.codeitems.service.ICodeItemsService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CodeItemValidator implements ConstraintValidator<CodeItem, String> {

    @Autowired
    ICodeItemsService iCodeItemsService;

    private boolean required;

    private String name;

    private String[] values;

    @Override
    public void initialize(CodeItem constraintAnnotation) {
        this.required = constraintAnnotation.required();
        this.name = constraintAnnotation.name();
        this.values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return true;
        }
        StringBuilder validValues = new StringBuilder();
        //优先判断数组内的值
        for (String val : values) {
            if (val.equals(value)) {
                return true;
            }
            validValues.append(val).append(", ");
        }
        if (StrUtil.isNotBlank(name)) {
            //如果字典名不为空，且入参不为空，则进行校验
            List<CodeItems> itemsList = iCodeItemsService.getCodeItemsByCodeName(name);
            for (CodeItems item : itemsList) {
                if (item.getItem_value().equals(value)) {
                    return true;
                }
                validValues.append(item.getItem_value()).append(" (").append(item.getItem_text()).append("), ");
            }
        }
        if (required) {
            String message = "Value '" + value + "' is not a valid code item. Valid values: " + validValues;
            message = message.substring(0, message.length() - 2);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
