package com.mirae.commerce.auth.utils;

public class RefreshTokenHolder {
    private static final ThreadLocal<String> refreshToken = ThreadLocal.withInitial(() -> null);

    public static String getRefreshToken() {
        /*
        if (UserContextHolder.refreshToken.get() != null) {
            // TODO : 에러코드 만들고 교체하기?
            throw new JwtExceptionHandler(ErrorCode.JWT_TOKEN_NOT_FOUND_ERROR);
        }
        */
        return RefreshTokenHolder.refreshToken.get();
    }
    public static void setRefreshToken(String refreshToken) {
        RefreshTokenHolder.refreshToken.set(refreshToken);
    }
}
