package com.mirae.commerce.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final T result;
    private final int resultCode;
    private final String resultMsg;

    @Builder
    public ApiResponse(final T result, final int resultCode, final String resultMsg) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
