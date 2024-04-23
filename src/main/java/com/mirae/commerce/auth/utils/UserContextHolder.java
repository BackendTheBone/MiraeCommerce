package com.mirae.commerce.auth.utils;

public class UserContextHolder {
    private static final ThreadLocal<String> currentUsername = ThreadLocal.withInitial(() -> "");

    public static String getCurrentUsername() {
        return UserContextHolder.currentUsername.get();
    }
    public static void setCurrentUsername(String currentUsername) {
        UserContextHolder.currentUsername.set(currentUsername);
    }
}
