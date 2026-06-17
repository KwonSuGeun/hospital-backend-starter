package com.hospital.staff.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA Entity — HOSPITAL.STAFF_DEPARTMENT 테이블 매핑
 */
@Entity
@Table(schema = "HOSPITAL", name = "STAFF_DEPARTMENT")
@Getter
@Setter
public class Department {

    @Id
    @Column(name = "DEPARTMENT_ID")
    private String id;

    @Column(name = "DEPARTMENT_NAME")
    private String name;

    @Column(name = "IS_ACTIVE")
    private String isActive;

    @Column(name = "STAFF_EXTENSION_NO")
    private String staffExtensionNo;
}
