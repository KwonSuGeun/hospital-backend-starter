package com.hospital.staff.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * [등록 폼] GET /api/staff/departments 응답 DTO
 * 프론트: DepartmentItem
 */
@Getter
@Setter
public class DepartmentDto {

    private String departmentId;
    private String departmentName;
}
