package com.hospital.sidebar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * MyBatis 조회 결과 row — parentId로 트리 조합 후 SidebarNodeDto로 변환
 */
@Getter
@Setter
public class SidebarMenuRowDto {

    private Long id;
    private Long parentId;
    private String label;
    private String path;
}
