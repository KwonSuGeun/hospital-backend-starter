package com.hospital.staff.mapper;

import com.hospital.staff.dto.DepartmentDto;
import com.hospital.staff.dto.StaffCreateRequestDto;
import com.hospital.staff.dto.StaffDetailDto;
import com.hospital.staff.dto.StaffDto;
import com.hospital.staff.entity.Department;
import com.hospital.staff.entity.Staff;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * MapStruct Mapper — Staff / Department Entity ↔ DTO 변환
 * 컴파일 시 StaffMapperImpl 이 자동 생성됨 (componentModel = "spring" → @Component)
 */
@Mapper(componentModel = "spring")
public interface StaffMapper {

    @Mapping(target = "photoUrl", ignore = true)
    StaffDto toDto(Staff staff);

    StaffDetailDto toDetailDto(Staff staff);

    @Mapping(target = "departmentId", source = "id")
    @Mapping(target = "departmentName", source = "name")
    DepartmentDto toDepartmentDto(Department department);

    List<StaffDto> toDtoList(List<Staff> staffList);

    List<DepartmentDto> toDepartmentDtoList(List<Department> departments);

    @Mapping(target = "id", source = "staffNo")
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "staffRoleCode", ignore = true)
    @Mapping(target = "staffStatus", ignore = true)
    @Mapping(target = "staffPhotoKey", ignore = true)
    @Mapping(target = "staffLicenseNo", ignore = true)
    @Mapping(target = "staffPositionCode", ignore = true)
    @Mapping(target = "staffExtensionNo", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "address", ignore = true)
    Staff toEntity(StaffCreateRequestDto request);

    @AfterMapping
    default void setPhotoUrl(@MappingTarget StaffDto dto, Staff staff) {
        if (StringUtils.hasText(staff.getStaffPhotoKey())) {
            dto.setPhotoUrl("/api/staff/" + staff.getId() + "/photo");
        }
    }

    @AfterMapping
    default void applyNullableFields(@MappingTarget Staff staff, StaffCreateRequestDto request) {
        staff.setStaffPositionCode(emptyToNull(request.getStaffPositionCode()));
        staff.setStaffExtensionNo(emptyToNull(request.getStaffExtensionNo()));
        staff.setEmail(emptyToNull(request.getEmail()));
        staff.setAddress(emptyToNull(request.getAddress()));
    }

    default String emptyToNull(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }
}
