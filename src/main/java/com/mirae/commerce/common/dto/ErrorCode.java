package com.mirae.commerce.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Member Service Error
    MEMBER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "M001", "unknown member error"),
    USERNAME_NOT_FOUND_ERROR(HttpStatus.BAD_REQUEST, "M002", "username not found"),
    PASSWORD_NOT_MATCH_ERROR(HttpStatus.BAD_REQUEST, "M003", "password not match"),

    // Jwt
    JWT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "J001", "unknown jwt error"),
    JWT_TOKEN_NOT_FOUND_ERROR(HttpStatus.UNAUTHORIZED, "J002", "jwt token not found"),
    EXPIRED_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "J003", "access token has expired"),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "J004", "refresh token has expired"),

    // Global Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "Internal Server Error Exception"),
    NULL_POINTER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G002", "Null Pointer Exception");


    private final HttpStatus status;
    private final String divisionCode;
    private final String message;

    ErrorCode(final HttpStatus status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}