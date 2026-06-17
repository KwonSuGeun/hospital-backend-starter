package com.hospital.staff.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * [목록] GET /api/staff 응답 DTO
 * 프론트: StaffItem
 */
@Getter
@Setter
public class StaffDto {

    private String id;
    private String name;
    private String departmentName;
    private String email;
    private String photoUrl;
}
