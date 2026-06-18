package com.hospital.staff.service;

import com.hospital.common.storage.SeaweedFsService;
import com.hospital.staff.dto.DepartmentDto;
import com.hospital.staff.dto.StaffCreateRequestDto;
import com.hospital.staff.dto.StaffDetailDto;
import com.hospital.staff.dto.StaffDto;
import com.hospital.staff.entity.Department;
import com.hospital.staff.entity.Staff;
import com.hospital.staff.mapper.StaffMapper;
import com.hospital.staff.repository.DepartmentRepository;
import com.hospital.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private final StaffMapper staffMapper;

    // --- [목록] ---
    @Override
    @Transactional(readOnly = true)
    public List<StaffDto> getStaffList() {
        List<Staff> staffList = staffRepository.findAll();
        return staffMapper.toDtoList(staffList);
    }

    // --- [상세] ---
    @Override
    @Transactional(readOnly = true)
    public StaffDetailDto getStaff(String id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("staff Error"));
        return staffMapper.toDetailDto(staff);
    }

    // --- [등록 폼] 부서 목록 ---
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> getDepartmentList() {
        List<Department> departments = departmentRepository.findByIsActiveOrderById("Y");
        return staffMapper.toDepartmentDtoList(departments);
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

        Staff staff = staffMapper.toEntity(request);
        staff.setDepartment(department);
        staff.setStaffRoleCode(ROLE_CODE_BY_TYPE.get(request.getStaffType()));
        staff.setStaffStatus(STAFF_STATUS_ACTIVE);
        staff.setStaffPhotoKey(photoKey);

        Staff savedStaff = staffRepository.save(staff);
        return staffMapper.toDto(savedStaff);
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
}
