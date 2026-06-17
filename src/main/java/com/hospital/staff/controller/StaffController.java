package com.hospital.staff.controller;

import com.hospital.common.ApiResponse;
import com.hospital.staff.dto.DepartmentDto;
import com.hospital.staff.dto.StaffCreateRequestDto;
import com.hospital.staff.dto.StaffDetailDto;
import com.hospital.staff.dto.StaffDto;
import com.hospital.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Staff REST API — /api/staff
 *
 * [등록 매핑]
 *   POST multipart: @RequestPart("staff") StaffCreateRequestDto + @RequestPart("photo") File?
 *   프론트: StaffCreateForm → toStaffCreatePayload() → createStaff()
 *
 * 프론트: staffApi.ts → staffSaga.ts / StaffRegister.tsx / StaffDetail.tsx
 */
@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    // --- [목록] GET /api/staff ---
    @GetMapping
    public ApiResponse<List<StaffDto>> getStaffList() {
        List<StaffDto> staffList = staffService.getStaffList();
        return ApiResponse.success(staffList);
    }

    // --- [등록 폼] GET /api/staff/departments (StaffRegister 부서 select) ---
    @GetMapping("/departments")
    public ApiResponse<List<DepartmentDto>> getDepartmentList() {
        List<DepartmentDto> departments = staffService.getDepartmentList();
        return ApiResponse.success(departments);
    }

    // --- [사진] GET /api/staff/{id}/photo (SeaweedFS 프록시) ---
    @GetMapping("/{id}/photo")
    public ResponseEntity<byte[]> getStaffPhoto(@PathVariable String id) {
        StaffService.PhotoResult photo = staffService.getStaffPhoto(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, photo.contentType())
                .body(photo.bytes());
    }

    // --- [상세] GET /api/staff/{id} ---
    @GetMapping("/{id}")
    public ApiResponse<StaffDetailDto> getStaff(@PathVariable String id) {
        StaffDetailDto staff = staffService.getStaff(id);
        return ApiResponse.success(staff);
    }

    // --- [등록] POST /api/staff (multipart: staff JSON + photo) ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<StaffDto> createStaff(
            @RequestPart("staff") StaffCreateRequestDto request,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        StaffDto staff = staffService.createStaff(request, photo);
        return ApiResponse.success(staff);
    }

    // --- [삭제] DELETE /api/staff/{id} ---
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStaff(@PathVariable String id) {
        staffService.deleteStaff(id);
        return ApiResponse.success(null);
    }
}
