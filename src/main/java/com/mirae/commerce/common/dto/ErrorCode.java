package com.mirae.commerce.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Member Service Error
    USERNAME_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "M001", "username not found"),
    PASSWORD_NOT_MATCH_ERROR(HttpStatus.BAD_REQUEST, "M002", "password not match"),

    // Jwt
    JWT_TOKEN_NOT_FOUND_ERROR(HttpStatus.UNAUTHORIZED, "J001", "jwt token not found"),
    EXPIRED_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "J002", "access token has expired"),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "J003", "refresh token has expired"),

    // Global Error
    NULL_POINTER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "Null Pointer Exception"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G999", "Internal Server Error Exception");


    private final HttpStatus status;
    private final String divisionCode;
    private final String message;

    ErrorCode(final HttpStatus status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}