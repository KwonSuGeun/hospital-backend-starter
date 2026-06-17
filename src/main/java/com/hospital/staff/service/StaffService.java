package com.hospital.staff.service;

import com.hospital.staff.dto.DepartmentDto;
import com.hospital.staff.dto.StaffCreateRequestDto;
import com.hospital.staff.dto.StaffDetailDto;
import com.hospital.staff.dto.StaffDto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Staff Service 인터페이스 — StaffController ↔ StaffServiceImpl
 */
public interface StaffService {

    List<StaffDto> getStaffList();

    StaffDetailDto getStaff(String id);

    StaffDto createStaff(StaffCreateRequestDto request, MultipartFile photo);

    List<DepartmentDto> getDepartmentList();

    PhotoResult getStaffPhoto(String id);

    void deleteStaff(String id);

    record PhotoResult(byte[] bytes, String contentType) {
    }
}
