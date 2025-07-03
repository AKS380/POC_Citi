package com.example.project1.service;

import com.example.project1.dto.DepartmentDto;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto dto);
    DepartmentDto updateDepartment(Long id, DepartmentDto dto);
    void deleteDepartment(Long id);
    DepartmentDto getById(Long id);
    DepartmentDto getByName(String name);
}
