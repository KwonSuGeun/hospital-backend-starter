package com.hospital.staff.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA Entity — HOSPITAL.STAFF 테이블 매핑
 * StaffRepository / StaffServiceImpl에서 사용
 */
@Entity
@Table(schema = "HOSPITAL", name = "STAFF")
@Getter
@Setter
public class Staff {

    @Id
    @Column(name = "STAFF_ID", length = 20)
    private String id;

    @Column(name = "STAFF_PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "STAFF_NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "STAFF_TYPE", nullable = false, length = 10)
    private String staffType;

    @Column(name = "STAFF_ROLE_CODE", nullable = false, length = 20)
    private String staffRoleCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAFF_DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID", nullable = false)
    private Department department;

    @Column(name = "STAFF_RANK_CODE", nullable = false, length = 10)
    private String staffRankCode;

    @Column(name = "STAFF_POSITION_CODE", length = 10)
    private String staffPositionCode;

    @Column(name = "STAFF_PHONE", nullable = false, length = 20)
    private String staffPhone;

    @Column(name = "STAFF_EXTENSION_NO", length = 20)
    private String staffExtensionNo;

    @Column(name = "STAFF_EMAIL", length = 100)
    private String email;

    @Column(name = "STAFF_HIRE_DATE", nullable = false)
    private LocalDate hireDate;

    @Column(name = "STAFF_STATUS", nullable = false, length = 10)
    private String staffStatus;

    @Column(name = "STAFF_BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @Column(name = "STAFF_LICENSE_NO", length = 50)
    private String staffLicenseNo;

    @Column(name = "STAFF_ADDRESS", length = 200)
    private String address;

    @Column(name = "STAFF_PHOTO_KEY", length = 200)
    private String staffPhotoKey;

    public String getDepartmentName() {
        if (department == null) {
            return null;
        }
        return department.getName();
    }
}
