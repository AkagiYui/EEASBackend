package com.kenko.ceea.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kenko.ceea.common.HTTPCode;
import com.kenko.ceea.entity.Teacher;
import com.kenko.ceea.exception.ServiceException;
import com.kenko.ceea.service.ITeacherService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private ITeacherService teacherService;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        // 获取 token
        String token = request.getHeader("Authorization");
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(HTTPCode.NOT_PERMIT, "token不能为空");
        }

        // 校验 jwt 合法性
        String teacherId;
        try {
            teacherId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new ServiceException(HTTPCode.NOT_PERMIT, "token无效");
        }

        // 验证 jwt 信息
        Teacher user = teacherService.getById(teacherId);
        if (user == null) {
            throw new ServiceException(HTTPCode.NOT_PERMIT, "用户不存在");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(HTTPCode.NOT_PERMIT, "token无效");
        }

        return true;
    }

}
