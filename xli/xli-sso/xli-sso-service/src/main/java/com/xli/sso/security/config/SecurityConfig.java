package com.xli.sso.security.config;


import com.xli.sso.security.filter.LoginFilter;
import com.xli.sso.security.filter.VerificationCodeFilter;
import com.xli.sso.security.handler.LoginFailureHandler;
import com.xli.sso.security.handler.LoginSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;

import java.io.IOException;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //permitAll:具有所有权限，也就是匿名可以访问
        //anyRequest:其他所有的
        //authenticated:必须登录认证才能访问
        httpSecurity.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/login").permitAll()
                    .anyRequest().authenticated();
        });
        //loginPage:登录页面
        //loginProcessingUrl:登录接口
        //defaultSuccessUrl:登录成功之后访问的页面
        //successHandler:登录成功处理器
        //failureHandler:登录失败处理器
        //自定义登录接口
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginProcessingUrl("/login")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailureHandler());
        });

        //在登录之前使用验证码过滤器
        httpSecurity.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);

        //配置自定义登录过滤器
        //将UsernamePasswordAuthenticationFilter替换掉
        httpSecurity.addFilterAt(new LoginFilter(), UsernamePasswordAuthenticationFilter.class);

        //rememberMeParameter：表单中记住我的name
        //key：是cookie加密的秘钥
        httpSecurity.rememberMe(e -> e.rememberMeParameter("rememberMe").rememberMeCookieName("rememberMe").key("myKey"));

        //跨域漏洞防御:关闭
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        //跨域拦截:关闭
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        httpSecurity.sessionManagement(e -> e.invalidSessionStrategy(new InvalidSessionStrategy() {
            //session失效策略
            @Override
            public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("会话过期");
            }
        }).maximumSessions(1).sessionRegistry(sessionRegistry()));

        //退出
        httpSecurity.logout(logout -> logout.invalidateHttpSession(true).deleteCookies("rememberMe").logoutSuccessUrl("logout").logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("退出成功");
            }
        }));

        return httpSecurity.build();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        loginFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
        loginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return loginFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return null;
    }

    /**
     * 获取所有当前登录的用户，以及设置用户过期
     *
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
