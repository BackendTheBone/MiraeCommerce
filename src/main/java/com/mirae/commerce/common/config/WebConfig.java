package com.mirae.commerce.common.config;

import com.mirae.commerce.auth.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    public static final String BASE_URL = "http://localhost:8080";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/member/**")
                .excludePathPatterns("/auth/**")
                .excludePathPatterns("/member/registration")
                .excludePathPatterns("/member/email-confirm");
    }
}
