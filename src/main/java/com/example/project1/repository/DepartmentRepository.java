package com.example.project1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDeptName(String name);
}
