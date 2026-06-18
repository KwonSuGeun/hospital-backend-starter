package com.hospital.sidebar.service;

import com.hospital.sidebar.dto.SidebarMenuRowDto;

import java.util.List;

/**
 * Sidebar Service 인터페이스 — SidebarController ↔ SidebarServiceImpl
 */
public interface SidebarService {

    List<SidebarMenuRowDto> getSidebarMenuRows();
}
