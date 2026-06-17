package com.hospital.sidebar.service;

import com.hospital.sidebar.dto.SidebarNodeDto;

import java.util.List;

/**
 * Sidebar Service 인터페이스 — SidebarController ↔ SidebarServiceImpl
 */
public interface SidebarService {

    List<SidebarNodeDto> getSidebarTree();
}
