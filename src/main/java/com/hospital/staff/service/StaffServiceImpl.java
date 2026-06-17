package com.hospital.staff.service;

import com.hospital.common.storage.SeaweedFsService;
import com.hospital.staff.dto.DepartmentDto;
import com.hospital.staff.dto.StaffCreateRequestDto;
import com.hospital.staff.dto.StaffDetailDto;
import com.hospital.staff.dto.StaffDto;
import com.hospital.staff.entity.Department;
import com.hospital.staff.entity.Staff;
import com.hospital.staff.repository.DepartmentRepository;
import com.hospital.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Staff 비즈니스 로직 — StaffController → StaffServiceImpl → Repository
 */
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    // --- 등록 시 기본값 / 검증 상수 ---
    private static final String STAFF_STATUS_ACTIVE = "재직";
    private static final Set<String> ALLOWED_STAFF_TYPES = Set.of("DOC", "NUR", "ADM");
    private static final Map<String, String> ROLE_CODE_BY_TYPE = Map.of(
            "DOC", "ROLE_DOCTOR",
            "NUR", "ROLE_NURSE",
            "ADM", "ROLE_ADMIN"
    );

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final SeaweedFsService seaweedFsService;

    // --- [목록] ---
    @Override
    @Transactional(readOnly = true)
    public List<StaffDto> getStaffList() {
        List<Staff> staffList = staffRepository.findAll();

        List<StaffDto> result = new ArrayList<>();
        for (Staff staff : staffList) {
            StaffDto dto = toDto(staff);
            result.add(dto);
        }
        return result;
    }

    // --- [상세] ---
    @Override
    @Transactional(readOnly = true)
    public StaffDetailDto getStaff(String id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("staff Error"));
        return toDetailDto(staff);
    }

    // --- [등록 폼] 부서 목록 ---
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> getDepartmentList() {
        List<Department> departments = departmentRepository.findByIsActiveOrderById("Y");

        List<DepartmentDto> result = new ArrayList<>();
        for (Department department : departments) {
            result.add(toDepartmentDto(department));
        }
        return result;
    }

    // --- [등록] ---
    @Override
    @Transactional
    public StaffDto createStaff(StaffCreateRequestDto request, MultipartFile photo) {
        if (staffRepository.existsById(request.getStaffNo())) {
            throw new RuntimeException("staff Error");
        }

        if (!ALLOWED_STAFF_TYPES.contains(request.getStaffType())) {
            throw new RuntimeException("staff Error");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("staff Error"));

        String photoKey = null;
        if (photo != null && !photo.isEmpty()) {
            photoKey = seaweedFsService.uploadStaffPhoto(request.getStaffNo(), photo);
        }

        Staff staff = new Staff();
        staff.setId(request.getStaffNo());
        staff.setPassword(request.getPassword());
        staff.setName(request.getName());
        staff.setStaffType(request.getStaffType());
        staff.setStaffRoleCode(ROLE_CODE_BY_TYPE.get(request.getStaffType()));
        staff.setDepartment(department);
        staff.setStaffRankCode(request.getStaffRankCode());
        staff.setStaffPositionCode(emptyToNull(request.getStaffPositionCode()));
        staff.setStaffPhone(request.getStaffPhone());
        staff.setStaffExtensionNo(emptyToNull(request.getStaffExtensionNo()));
        staff.setEmail(emptyToNull(request.getEmail()));
        staff.setHireDate(request.getHireDate());
        staff.setStaffStatus(STAFF_STATUS_ACTIVE);
        staff.setBirthDate(request.getBirthDate());
        staff.setAddress(emptyToNull(request.getAddress()));
        staff.setStaffPhotoKey(photoKey);

        Staff savedStaff = staffRepository.save(staff);
        return toDto(savedStaff);
    }

    @Override
    @Transactional(readOnly = true)
    public PhotoResult getStaffPhoto(String id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("staff Error"));

        if (!StringUtils.hasText(staff.getStaffPhotoKey())) {
            throw new RuntimeException("staff Error");
        }

        SeaweedFsService.PhotoData photoData = seaweedFsService.download(staff.getStaffPhotoKey());
        return new PhotoResult(photoData.bytes(), photoData.contentType());
    }

    @Override
    @Transactional
    public void deleteStaff(String id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("staff Error"));

        String photoKey = staff.getStaffPhotoKey();
        staffRepository.delete(staff);

        if (StringUtils.hasText(photoKey)) {
            try {
                seaweedFsService.delete(photoKey);
            } catch (RuntimeException exception) {
                // SeaweedFS 삭제 실패 시 DB 삭제는 유지
            }
        }
    }

    // --- 내부 헬퍼 / DTO 변환 ---
    private String emptyToNull(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }

    private StaffDto toDto(Staff staff) {
        StaffDto dto = new StaffDto();
        dto.setId(staff.getId());
        dto.setName(staff.getName());
        dto.setEmail(staff.getEmail());
        dto.setDepartmentName(staff.getDepartmentName());
        if (StringUtils.hasText(staff.getStaffPhotoKey())) {
            dto.setPhotoUrl("/api/staff/" + staff.getId() + "/photo");
        }
        return dto;
    }

    private StaffDetailDto toDetailDto(Staff staff) {
        StaffDetailDto dto = new StaffDetailDto();
        dto.setId(staff.getId());
        dto.setName(staff.getName());
        dto.setDepartmentName(staff.getDepartmentName());
        dto.setStaffType(staff.getStaffType());
        dto.setStaffRankCode(staff.getStaffRankCode());
        dto.setStaffPositionCode(staff.getStaffPositionCode());
        dto.setStaffPhone(staff.getStaffPhone());
        dto.setStaffExtensionNo(staff.getStaffExtensionNo());
        dto.setEmail(staff.getEmail());
        dto.setHireDate(staff.getHireDate());
        dto.setStaffStatus(staff.getStaffStatus());
        dto.setBirthDate(staff.getBirthDate());
        dto.setAddress(staff.getAddress());
        return dto;
    }

    private DepartmentDto toDepartmentDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setDepartmentId(department.getId());
        dto.setDepartmentName(department.getName());
        return dto;
    }
}
