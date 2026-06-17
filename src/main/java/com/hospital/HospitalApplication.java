package com.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 진입점 — JPA(Staff) + MyBatis(Sidebar) 통합
 * 기본 포트: 8081
 */
@SpringBootApplication
@MapperScan("com.hospital.sidebar.mapper")
public class HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }
}
