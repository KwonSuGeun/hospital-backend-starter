package com.hospital.sidebar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * MyBatis 조회 결과 row — GET /api/sidebar flat 응답 (트리 조합은 프론트)
 */
@Getter
@Setter
public class SidebarMenuRowDto {

    private Long id;
    private Long parentId;
    private String label;
    private String path;
}
