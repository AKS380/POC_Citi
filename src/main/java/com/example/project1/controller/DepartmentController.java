package com.example.project1.controller;

import com.example.project1.dto.DepartmentDto;
import com.example.project1.service.DepartmentService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentDto create(@RequestBody DepartmentDto dto) {
        return departmentService.createDepartment(dto);
    }

    @PutMapping("/{id}")
    public DepartmentDto update(@PathVariable Long id, @RequestBody DepartmentDto dto) {
        return departmentService.updateDepartment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @GetMapping("/{id}")
    public DepartmentDto getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @GetMapping("/name/{name}")
    public DepartmentDto getByName(@PathVariable String name) {
        return departmentService.getByName(name);
    }
}
