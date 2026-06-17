package com.hospital.sidebar.controller;

import com.hospital.common.ApiResponse;
import com.hospital.sidebar.dto.SidebarNodeDto;
import com.hospital.sidebar.service.SidebarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Sidebar REST API — /api/sidebar
 * 프론트: sidebarApi.ts → sidebarSaga.ts → sidebar.tsx
 */
@RestController
@RequestMapping("/api/sidebar")
@RequiredArgsConstructor
public class SidebarController {

    private final SidebarService sidebarService;

    // --- [메뉴 트리] GET /api/sidebar ---
    @GetMapping
    public ApiResponse<List<SidebarNodeDto>> getSidebarTree() {
        return ApiResponse.success(sidebarService.getSidebarTree());
    }
}
