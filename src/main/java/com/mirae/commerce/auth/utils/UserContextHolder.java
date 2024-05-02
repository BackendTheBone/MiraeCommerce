package com.mirae.commerce.auth.utils;

import com.mirae.commerce.auth.exception.JwtExceptionHandler;
import com.mirae.commerce.common.dto.ErrorCode;
import org.springframework.beans.factory.ObjectProvider;

// TODO : request scope 컴포넌트로 변경 검토해볼 것
public class UserContextHolder {
    private static final ThreadLocal<String> currentUsername = ThreadLocal.withInitial(() -> null);

    public static String getCurrentUsername() {
        if (UserContextHolder.currentUsername.get() != null) {
            // TODO : 에러코드 만들고 교체하기
            throw new JwtExceptionHandler(ErrorCode.JWT_TOKEN_NOT_FOUND_ERROR);
        }
        return UserContextHolder.currentUsername.get();
    }
    public static void setCurrentUsername(String currentUsername) {
        UserContextHolder.currentUsername.set(currentUsername);
    }
}
