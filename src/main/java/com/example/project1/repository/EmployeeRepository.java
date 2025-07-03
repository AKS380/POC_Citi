package com.example.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project1.entity.Employee;




public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    
    
}
