package com.hospital.staff.repository;

import com.hospital.staff.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Department JPA Repository — 등록 폼 부서 select (IS_ACTIVE = 'Y')
 */
public interface DepartmentRepository extends JpaRepository<Department, String> {

    List<Department> findByIsActiveOrderById(String isActive);
}
