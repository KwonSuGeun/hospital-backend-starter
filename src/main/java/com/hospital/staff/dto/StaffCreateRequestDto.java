package com.hospital.staff.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * [등록] POST /api/staff — @RequestPart("staff") JSON body
 *
 * 프론트 매핑:
 *   StaffCreateForm (폼 state)
 *     → toStaffCreatePayload() — staffCreateMapper.ts
 *     → multipart: staff(본 DTO JSON) + photo(File, optional)
 *
 * 서버 기본값 (StaffServiceImpl):
 *   staffStatus = "재직"
 *   staffRoleCode = staffType 에 따라 DOC/NUR/ADM → ROLE_* 자동 설정
 *   staffPhotoKey = photo 업로드 시 SeaweedFS object key
 *
 * DB 매핑 (Staff 엔티티):
 *   staffNo        → STAFF_ID
 *   password       → STAFF_PASSWORD
 *   name           → STAFF_NAME
 *   departmentId   → STAFF_DEPARTMENT_ID (FK)
 *   staffType      → STAFF_TYPE
 *   staffRankCode  → STAFF_RANK_CODE
 *   staffPositionCode → STAFF_POSITION_CODE (빈값 → null)
 *   staffPhone     → STAFF_PHONE
 *   staffExtensionNo  → STAFF_EXTENSION_NO (빈값 → null)
 *   email          → STAFF_EMAIL (빈값 → null)
 *   address        → STAFF_ADDRESS (빈값 → null)
 *   hireDate       → STAFF_HIRE_DATE
 *   birthDate      → STAFF_BIRTH_DATE
 */
@Getter
@Setter
public class StaffCreateRequestDto {

    private String staffNo;
    private String password;
    private String name;
    private String departmentId;
    private String staffType;
    private String staffRankCode;
    private String staffPositionCode;
    private String staffPhone;
    private String staffExtensionNo;
    private String email;
    private String address;
    private LocalDate hireDate;
    private LocalDate birthDate;
}
