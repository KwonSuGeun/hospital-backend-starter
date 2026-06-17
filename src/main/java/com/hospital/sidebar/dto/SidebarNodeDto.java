package com.hospital.sidebar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * [메뉴 트리] GET /api/sidebar 응답 노드 DTO
 * 프론트: SidebarItem (children 재귀 구조)
 */
@Getter
@Setter
public class SidebarNodeDto {

    private Long id;
    private String label;
    private String path;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SidebarNodeDto> children = new ArrayList<>();
}
