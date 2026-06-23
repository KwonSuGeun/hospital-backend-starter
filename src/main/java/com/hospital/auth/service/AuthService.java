package com.hospital.auth.service;

import com.hospital.auth.dto.AuthUserDto;
import com.hospital.auth.dto.LoginRequestDto;
import com.hospital.staff.entity.Staff;
import com.hospital.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 로그인 검증 — STAFF 테이블 ID/비밀번호 확인
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    public static final String SESSION_USER_KEY = "LOGIN_USER";
    private static final String STAFF_STATUS_ACTIVE = "재직";

    private final StaffRepository staffRepository;

    @Transactional(readOnly = true)
    public AuthUserDto login(LoginRequestDto request) {
        if (!StringUtils.hasText(request.getStaffId()) || !StringUtils.hasText(request.getPassword())) {
            throw new IllegalArgumentException("사번과 비밀번호를 입력하세요.");
        }

        Staff staff = staffRepository.findById(request.getStaffId().trim())
                .orElseThrow(() -> new IllegalArgumentException("사번 또는 비밀번호가 올바르지 않습니다."));

        if (!STAFF_STATUS_ACTIVE.equals(staff.getStaffStatus())) {
            throw new IllegalArgumentException("사번 또는 비밀번호가 올바르지 않습니다.");
        }

        if (!request.getPassword().equals(staff.getPassword())) {
            throw new IllegalArgumentException("사번 또는 비밀번호가 올바르지 않습니다.");
        }

        AuthUserDto user = new AuthUserDto();
        user.setStaffId(staff.getId());
        user.setName(staff.getName());
        user.setStaffRoleCode(staff.getStaffRoleCode());
        return user;
    }
}
