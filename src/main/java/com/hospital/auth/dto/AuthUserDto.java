package com.hospital.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 성공 / GET /api/auth/me 응답 — 프론트 localStorage 저장용
 */
@Getter
@Setter
public class AuthUserDto {

    private String staffId;
    private String name;
    private String staffRoleCode;
}
