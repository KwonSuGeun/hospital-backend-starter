package com.hospital.staff.repository;

import com.hospital.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Staff JPA Repository — HOSPITAL.STAFF CRUD
 */
public interface StaffRepository extends JpaRepository<Staff, String> {
}
