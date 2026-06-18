package com.hospital.sidebar.mapper;

import com.hospital.sidebar.dto.SidebarMenuRowDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MyBatis Mapper — AUTH_MENU flat 조회 (GET /api/sidebar)
 */
@Mapper
public interface SidebarMapper {

    List<SidebarMenuRowDto> selectSidebarMenuRows();
}
