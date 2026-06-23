package com.hospital.auth.controller;

import com.hospital.auth.dto.AuthUserDto;
import com.hospital.auth.dto.LoginRequestDto;
import com.hospital.auth.service.AuthService;
import com.hospital.common.ApiResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인 REST API — /api/auth
 * 세션(HttpSession)에 사용자 저장, 프론트는 userInfo만 localStorage 저장
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // --- [로그인] POST /api/auth/login ---
    @PostMapping("/login")
    public ApiResponse<AuthUserDto> login(@RequestBody LoginRequestDto request, HttpSession session) {
        AuthUserDto user = authService.login(request);
        session.setAttribute(AuthService.SESSION_USER_KEY, user);
        return ApiResponse.success(user);
    }

    // --- [세션 확인] GET /api/auth/me ---
    @GetMapping("/me")
    public ApiResponse<AuthUserDto> me(HttpSession session) {
        AuthUserDto user = (AuthUserDto) session.getAttribute(AuthService.SESSION_USER_KEY);
        if (user == null) {
            return ApiResponse.error("UNAUTHORIZED", "로그인이 필요합니다.");
        }
        return ApiResponse.success(user);
    }

    // --- [로그아웃] POST /api/auth/logout ---
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success(null);
    }
}
