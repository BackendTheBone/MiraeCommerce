package com.mirae.commerce.auth.jwt;

import com.mirae.commerce.auth.exception.JwtExceptionHandler;
import com.mirae.commerce.auth.utils.UserContextHolder;
import com.mirae.commerce.common.dto.ErrorCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * JWT로부터 파싱한 정보를 매개변수에 주입해주는 역할을 수행
 */
@Aspect
@Component
public class JwtAspect {
    @Before("@annotation(com.mirae.commerce.auth.jwt.JwtRequired)")
    void injectUsername(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        for (Annotation[] annotations : methodSignature.getMethod().getParameterAnnotations()) {
            for (int i=0; i<annotations.length; i++) {
                if (!annotations[i].annotationType().equals(JwtUsernameInject.class)) {
                    continue;
                }

                try {
                    Method usernameSetter = args[i].getClass().getMethod("setUsername", String.class);
                    usernameSetter.invoke(args[i], UserContextHolder.getCurrentUsername());
                    break;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new JwtExceptionHandler("Failed to inject username", ErrorCode.JWT_ERROR);
                }
            }
        };
    }
}
