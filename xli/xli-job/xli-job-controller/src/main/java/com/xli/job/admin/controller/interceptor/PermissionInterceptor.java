package com.xli.job.admin.controller.interceptor;

import com.xli.job.admin.controller.annotation.PermissionLimit;
import com.xli.job.admin.core.model.XxlJobUser;
import com.xli.job.admin.core.util.I18nUtil;
import com.xli.job.admin.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;

@Component
public class PermissionInterceptor implements AsyncHandlerInterceptor {

	@Resource
	private LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if (!(handler instanceof HandlerMethod)) {
			return true;	// proceed with the next interceptor
		}

		// if need login
		boolean needLogin = true;
		boolean needAdminuser = false;
		HandlerMethod method = (HandlerMethod)handler;
		PermissionLimit permission = method.getMethodAnnotation(PermissionLimit.class);
		if (permission!=null) {
			needLogin = permission.limit();
			needAdminuser = permission.adminuser();
		}

		if (needLogin) {
			XxlJobUser loginUser = loginService.ifLogin(request, response);
			if (loginUser == null) {
				response.setStatus(302);
				response.setHeader("location", request.getContextPath()+"/toLogin");
				return false;
			}
			if (needAdminuser && loginUser.getRole()!=1) {
				throw new RuntimeException(I18nUtil.getString("system_permission_limit"));
			}
			request.setAttribute(LoginService.LOGIN_IDENTITY_KEY, loginUser);
		}

		return true;	// proceed with the next interceptor
	}
	
}
