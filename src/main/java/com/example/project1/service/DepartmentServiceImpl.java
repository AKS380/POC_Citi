package com.example.project1.service;

import com.example.project1.dto.DepartmentDto;
import com.example.project1.entity.Department;
import com.example.project1.mapper.DepartmentMapper;
import com.example.project1.repository.DepartmentRepository;
import com.example.project1.exception.DepartmentNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto dto) {
        Department dept = DepartmentMapper.mapToEntity(dto);
        Department saved = departmentRepository.save(dept);
        return DepartmentMapper.mapToDto(saved);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto dto) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        existing.setDeptName(dto.getDeptName());
        existing.setActive(dto.isActive());
        Department updated = departmentRepository.save(existing);
        return DepartmentMapper.mapToDto(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentDto getById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        return DepartmentMapper.mapToDto(dept);
    }

    @Override
    public DepartmentDto getByName(String name) {
        Department dept = departmentRepository.findByDeptName(name)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        return DepartmentMapper.mapToDto(dept);
    }
}
