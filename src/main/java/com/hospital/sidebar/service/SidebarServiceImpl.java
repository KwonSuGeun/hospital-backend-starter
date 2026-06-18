package com.hospital.sidebar.service;

import com.hospital.sidebar.dto.SidebarMenuRowDto;
import com.hospital.sidebar.mapper.SidebarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Sidebar 비즈니스 로직 — MyBatis SidebarMapper → AUTH_MENU flat 조회
 */
@Service
@RequiredArgsConstructor
public class SidebarServiceImpl implements SidebarService {

    private final SidebarMapper sidebarMapper;

    // --- [메뉴] AUTH_MENU flat row 조회 (트리 조합은 프론트) ---
    @Override
    public List<SidebarMenuRowDto> getSidebarMenuRows() {
        return sidebarMapper.selectSidebarMenuRows();
    }
}
