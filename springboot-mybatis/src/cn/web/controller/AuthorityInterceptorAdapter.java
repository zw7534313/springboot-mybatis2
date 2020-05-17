package cn.web.controller;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorityInterceptorAdapter extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthorityInterceptorAdapter.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //处理Permission Annotation，实现方法级权限控制 
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
             /*
             * 1、确认当前的controller是否需要进行权限判定，如果需要则进行验证。
             * 2、当controller不需要验证，则验证当前的方法是否需要权限验证，需要则进行验证，不需要则跳出
             * */
            //获取controller注解， controller检查是否需要验证权限控制 
            AuthorityValid permission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AuthorityValid.class);
            if (permission != null && !permission.validate()) { //不需要验证权限
                return super.preHandle(request, response, handler);
            }
             //获取方法注解，方法检查是否需要验证权限控制
            permission = handlerMethod.getMethod().getAnnotation(AuthorityValid.class);
            if (permission != null && permission.validate()) { //不需要验证权限
            	Parameter[] args = handlerMethod.getMethod().getParameters();
            	logger.info("参数：{} 类型{}", args[0].getName(), args[0].getType());
            	MethodParameter[] params = handlerMethod.getMethodParameters();
            	String paramName = params[0].getParameterName();
            	String value = request.getParameter(paramName);
            	logger.info("参数名称：{}={}", paramName, value);
            	return super.preHandle(request, response, handler);
            }
             // 权限判断,没有权限则跳转至无权限页面，有权限则走正常流程
            logger.info("权限判断...");
        }
        return super.preHandle(request, response, handler);
    } 
}
