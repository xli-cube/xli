package com.xli.log.server.config;


import com.alibaba.fastjson.JSONObject;
import com.xli.log.server.controller.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.PrintWriter;
import java.util.Objects;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String url=request.getRequestURI();
        if(url.contains("sendRunLog") || url.contains("sendTraceLog")|| url.contains("sendLog")){
            return true;
        }
        if (!Objects.equals(request.getMethod(), HttpMethod.POST.name())) {
            return true;
        }
        if (StringUtils.isEmpty(InitConfig.loginUsername))
            return true;
        Object token = request.getSession().getAttribute("token");
        if (token == null) {
            printMessage(response, Result.UN_LOGIN);
            return false;
        }
        return true;
    }


    private void printMessage(HttpServletResponse response, Result rm) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSONObject.toJSONString(rm));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
