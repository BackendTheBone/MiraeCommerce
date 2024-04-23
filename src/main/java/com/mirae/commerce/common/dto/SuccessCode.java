package com.mirae.commerce.common.dto;

import lombok.Getter;

@Getter
public enum SuccessCode {
    DEFAULT_SUCCESS(200, "200", "SUCCESS");

    private final int status;
    private final String code;
    private final String message;

    SuccessCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
