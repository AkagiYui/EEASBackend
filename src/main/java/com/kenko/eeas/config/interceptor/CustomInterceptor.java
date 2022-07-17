package com.kenko.eeas.config.interceptor;

import cn.hutool.log.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomInterceptor implements HandlerInterceptor {

    private static final Log log = Log.get();

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        log.info("{}: {}", request.getMethod(), request.getRequestURI());
        return true;
    }

}
