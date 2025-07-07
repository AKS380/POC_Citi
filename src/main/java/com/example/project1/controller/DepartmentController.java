package com.example.project1.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.DepartmentDto;
import com.example.project1.service.DepartmentService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentDto create(@RequestBody DepartmentDto dto) {
        log.trace("TRACE: Entered create department method");
        log.debug("DEBUG: DepartmentDto received: {}", dto);
        log.info("INFO: Creating new department");
        try {
            DepartmentDto created = departmentService.createDepartment(dto);
            log.info("INFO: Department created successfully: {}", created);
            return created;
        } catch (Exception e) {
            log.error("ERROR: Exception while creating department", e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public DepartmentDto update(@PathVariable Long id, @RequestBody DepartmentDto dto) {
        log.trace("TRACE: Entered update department method");
        log.debug("DEBUG: Updating department with ID: {}, Data: {}", id, dto);
        log.info("INFO: Updating department with ID: {}", id);
        try {
            DepartmentDto updated = departmentService.updateDepartment(id, dto);
            log.info("INFO: Department updated successfully: {}", updated);
            return updated;
        } catch (Exception e) {
            log.error("ERROR: Exception while updating department", e);
            throw e;
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.trace("TRACE: Entered delete department method");
        log.debug("DEBUG: Deleting department with ID: {}", id);
        try {
            departmentService.deleteDepartment(id);
            log.warn("WARN: Department with ID {} deleted", id);
        } catch (Exception e) {
            log.error("ERROR: Exception while deleting department", e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public DepartmentDto getById(@PathVariable Long id) {
        log.trace("TRACE: Entered getById method");
        log.debug("DEBUG: Fetching department with ID: {}", id);
        DepartmentDto dept = departmentService.getById(id);
        if (dept == null) {
            log.warn("WARN: Department with ID {} not found", id);
        } else {
            log.info("INFO: Department found: {}", dept);
        }
        return dept;
    }

     @GetMapping("/name/{name}")
    public DepartmentDto getByName(@PathVariable String name) {
        log.trace("TRACE: Entered getByName method");
        log.debug("DEBUG: Fetching department with name: {}", name);
        DepartmentDto dept = departmentService.getByName(name);
        if (dept == null) {
            log.warn("WARN: Department with name '{}' not found", name);
        } else {
            log.info("INFO: Department found: {}", dept);
        }
        return dept;
    }
}
