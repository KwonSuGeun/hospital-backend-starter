package com.hospital.auth.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * POST /api/auth/login 요청 body
 */
@Getter
@Setter
public class LoginRequestDto {

    private String staffId;
    private String password;
}
