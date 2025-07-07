package com.example.project1.service;

import org.springframework.stereotype.Service;

import com.example.project1.dto.DepartmentDto;
import com.example.project1.entity.Department;
import com.example.project1.exception.DepartmentNotFoundException;
import com.example.project1.mapper.DepartmentMapper;
import com.example.project1.repository.DepartmentRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

     @Override
    public DepartmentDto createDepartment(DepartmentDto dto) {
        log.trace("TRACE: Entered createDepartment in service");
        log.debug("DEBUG: DepartmentDto received: {}", dto);
        log.info("INFO: Creating new department");
        try {
            Department dept = DepartmentMapper.mapToEntity(dto);
            Department saved = departmentRepository.save(dept);
            log.info("INFO: Department created successfully: {}", saved);
            return DepartmentMapper.mapToDto(saved);
        } catch (Exception e) {
            log.error("ERROR: Exception while creating department", e);
            throw e;
        }
    }
     @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto dto) {
        log.trace("TRACE: Entered updateDepartment in service");
        log.debug("DEBUG: Updating department with ID: {}, Data: {}", id, dto);
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("WARN: Department with ID {} not found for update", id);
                    return new DepartmentNotFoundException("Department not found");
                });
        existing.setDeptName(dto.getDeptName());
        existing.setActive(dto.isActive());
        Department updated = departmentRepository.save(existing);
        log.info("INFO: Department updated successfully: {}", updated);
        return DepartmentMapper.mapToDto(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        log.trace("TRACE: Entered deleteDepartment in service");
        if (!departmentRepository.existsById(id)) {
            log.warn("WARN: Department with ID {} not found for deletion", id);
            throw new DepartmentNotFoundException("Department not found");
        }
        departmentRepository.deleteById(id);
        log.info("INFO: Department with ID {} deleted successfully", id);
    }
    @Override
    public DepartmentDto getById(Long id) {
        log.trace("TRACE: Entered getById in service");
        log.debug("DEBUG: Fetching department with ID: {}", id);
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("WARN: Department with ID {} not found", id);
                    return new DepartmentNotFoundException("Department not found");
                });
        log.info("INFO: Department found: {}", dept);
        return DepartmentMapper.mapToDto(dept);
    }

    @Override
    public DepartmentDto getByName(String name) {
        log.trace("TRACE: Entered getByName in service");
        log.debug("DEBUG: Fetching department with name: {}", name);
        Department dept = departmentRepository.findByDeptName(name)
                .orElseThrow(() -> {
                    log.warn("WARN: Department with name '{}' not found", name);
                    return new DepartmentNotFoundException("Department not found");
                });
        log.info("INFO: Department found: {}", dept);
        return DepartmentMapper.mapToDto(dept);
    }
}
