package com.mirae.commerce.member.exception;

import com.mirae.commerce.common.dto.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberExceptionHandler extends RuntimeException {
    private final ErrorCode errorCode;

    @Builder
    public MemberExceptionHandler(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public MemberExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
