package com.example.project1.mapper;

import com.example.project1.dto.DepartmentDto;
import com.example.project1.entity.Department;

public class DepartmentMapper {
      public static DepartmentDto mapToDto(Department dept) {
        DepartmentDto dto = new DepartmentDto();
        dto.setDeptId(dept.getDeptId());
        dto.setDeptName(dept.getDeptName());
        dto.setActive(dept.isActive());
        return dto;
    }

    public static Department mapToEntity(DepartmentDto dto) {
        Department dept = new Department();
        dept.setDeptId(dto.getDeptId());
        dept.setDeptName(dto.getDeptName());
        dept.setActive(dto.isActive());
        return dept;
    }
    
}