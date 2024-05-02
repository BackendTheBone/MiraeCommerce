package com.mirae.commerce.auth.interceptor;

import com.mirae.commerce.auth.exception.JwtExceptionHandler;
import com.mirae.commerce.auth.jwt.JwtRequired;
import com.mirae.commerce.auth.utils.UserContextHolder;
import com.mirae.commerce.common.dto.ErrorCode;
import com.mirae.commerce.member.exception.MemberExceptionHandler;
import com.mirae.commerce.auth.jwt.JwtProvider;
import com.mirae.commerce.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 인증 및 인가를 수행하는 인터셉터
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(JwtRequired.class) == null) {
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authorizationHeader.substring(7);

        Claims claims = null;
        try {
            claims = jwtProvider.getClaims(token);
        } catch (ExpiredJwtException e) {
            throw new JwtExceptionHandler(ErrorCode.EXPIRED_ACCESS_TOKEN_EXCEPTION);
        }

        if (claims == null || !claims.containsKey("username")) {
            throw new JwtExceptionHandler(ErrorCode.JWT_ERROR);
        }

        String username = (String) claims.get("username");
        memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        UserContextHolder.setCurrentUsername(username);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}