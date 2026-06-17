package com.hospital.sidebar.service;

import com.hospital.sidebar.dto.SidebarMenuRowDto;
import com.hospital.sidebar.dto.SidebarNodeDto;
import com.hospital.sidebar.mapper.SidebarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Sidebar 비즈니스 로직 — MyBatis SidebarMapper → PARENT_ID 기준 트리 구성
 */
@Service
@RequiredArgsConstructor
public class SidebarServiceImpl implements SidebarService {

    private final SidebarMapper sidebarMapper;

    // --- [메뉴 트리] AUTH_MENU 조회 후 buildTree ---
    @Override
    public List<SidebarNodeDto> getSidebarTree() {
        List<SidebarMenuRowDto> rows = sidebarMapper.selectSidebarMenuRows();
        return buildTree(rows);
    }

    // --- flat rows → nested tree (parentId 연결) ---
    private List<SidebarNodeDto> buildTree(List<SidebarMenuRowDto> rows) {
        Map<Long, SidebarNodeDto> nodeMap = new LinkedHashMap<>();
        List<SidebarNodeDto> roots = new ArrayList<>();

        for (SidebarMenuRowDto row : rows) {
            SidebarNodeDto node = new SidebarNodeDto();
            node.setId(row.getId());
            node.setLabel(row.getLabel());
            node.setPath(row.getPath());
            nodeMap.put(row.getId(), node);
        }

        for (SidebarMenuRowDto row : rows) {
            SidebarNodeDto node = nodeMap.get(row.getId());

            if (row.getParentId() == null) {
                roots.add(node);
                continue;
            }

            SidebarNodeDto parent = nodeMap.get(row.getParentId());
            if (parent != null) {
                parent.getChildren().add(node);
            }
        }

        return roots;
    }
}
