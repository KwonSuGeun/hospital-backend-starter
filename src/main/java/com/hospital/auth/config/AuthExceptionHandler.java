package com.hospital.auth.config;

import com.hospital.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 로그인 실패 등 auth 관련 예외 → ApiResponse 형식으로 반환
 */
@RestControllerAdvice(basePackages = "com.hospital.auth")
public class AuthExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleIllegalArgument(IllegalArgumentException exception) {
        return ApiResponse.error("AUTH_FAILED", exception.getMessage());
    }
}
