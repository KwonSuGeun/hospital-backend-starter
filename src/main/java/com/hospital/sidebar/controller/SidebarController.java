package com.hospital.sidebar.controller;

import com.hospital.common.ApiResponse;
import com.hospital.sidebar.dto.SidebarMenuRowDto;
import com.hospital.sidebar.service.SidebarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Sidebar REST API — /api/sidebar
 * 프론트: sidebarApi.ts → buildSidebarTree → sidebarSaga.ts → sidebar.tsx
 */
@RestController
@RequestMapping("/api/sidebar")
@RequiredArgsConstructor
public class SidebarController {

    private final SidebarService sidebarService;

    // --- [메뉴] GET /api/sidebar — flat row (parentId 포함) ---
    @GetMapping
    public ApiResponse<List<SidebarMenuRowDto>> getSidebarMenuRows() {
        return ApiResponse.success(sidebarService.getSidebarMenuRows());
    }
}
