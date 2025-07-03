package com.example.project1.service;
import java.util.List;

import com.example.project1.dto.EmployeeDto;

public interface EmployeeService {
     public EmployeeDto createNewEmployee(EmployeeDto EmployeeDto);

     public List<EmployeeDto> getAllEmployees();

     public EmployeeDto getEmployeeById(Long id);

     public EmployeeDto updateEmployeeById(Long id, EmployeeDto EmployeeDto);

     public void deleteEmployeeById(Long id);
}