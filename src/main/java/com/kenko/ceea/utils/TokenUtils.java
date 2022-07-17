package com.kenko.ceea.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kenko.ceea.entity.Teacher;
import com.kenko.ceea.service.ITeacherService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static final Log log = Log.get();
    private static ITeacherService staticTeacherService;
    @Resource
    private ITeacherService teacherService;

    @PostConstruct
    public void setTeacherService() {
        staticTeacherService = teacherService;
    }

    public static String generateToken(String teacherId, String sign) {
        return JWT.create().withAudience(teacherId)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))  // 设置过期时间2小时
                .sign(Algorithm.HMAC256(sign));
    }

    public static Teacher getCurrentTeacher() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            HttpServletRequest request = requestAttributes.getRequest();
            String token = request.getHeader("Authorization");
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            if (StrUtil.isNotBlank(token)) {
                String teacherId = JWT.decode(token).getAudience().get(0);
                return staticTeacherService.getById(teacherId);
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
