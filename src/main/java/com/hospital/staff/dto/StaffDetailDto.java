package com.hospital.staff.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * [상세] GET /api/staff/{id} 응답 DTO
 * 프론트: StaffDetailItem
 */
@Getter
@Setter
public class StaffDetailDto {

    private String id;
    private String name;
    private String departmentName;
    private String staffType;
    private String staffRankCode;
    private String staffPositionCode;
    private String staffPhone;
    private String staffExtensionNo;
    private String email;
    private LocalDate hireDate;
    private String staffStatus;
    private LocalDate birthDate;
    private String address;
}
