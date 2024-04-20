package com.mirae.commerce.common.exception;

import com.mirae.commerce.auth.exception.JwtExceptionHandler;
import com.mirae.commerce.common.dto.ErrorCode;
import com.mirae.commerce.common.dto.ErrorResponse;
import com.mirae.commerce.member.exception.MemberExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    모든 메서드는 반환할 때마다 ResponseEntity의 상태를 직접 적고 있다.
    ErrorCode로부터 HttpStatus를 읽어 오는 것이 더 바람직한 방법인지 확인할 것.
    */
    @ExceptionHandler(MemberExceptionHandler.class)
    protected ResponseEntity<ErrorResponse> handleMemberException(MemberExceptionHandler e) {
        log.error("memberException", e);
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtExceptionHandler.class)
    protected ResponseEntity<ErrorResponse> handleJwtException(JwtExceptionHandler e) {
        log.error("jwtException", e);
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        log.error("handleNullPointerException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.NULL_POINTER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ArithmeticException.class)
    protected ResponseEntity<ErrorResponse> handleArithmeticException(ArithmeticException e) {
        log.error("ArithmeticException Occured!", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Exception", ex);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
