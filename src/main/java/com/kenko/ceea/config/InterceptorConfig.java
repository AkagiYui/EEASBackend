package com.kenko.ceea.config;

import com.kenko.ceea.config.interceptor.CustomInterceptor;
import com.kenko.ceea.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/user/login", "/survey/submit/*", "/survey/sheet/*")
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/v3/**",
                        "/error",
                        "/v2/api-docs",
                        "/swagger-ui.html"
                );
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    public CustomInterceptor customInterceptor() {
        return new CustomInterceptor();
    }
}
