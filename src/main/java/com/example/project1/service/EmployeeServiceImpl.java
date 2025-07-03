package com.example.project1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.project1.dto.DepartmentDto;
import com.example.project1.dto.EmployeeDto;
import com.example.project1.entity.Employee;
import com.example.project1.exception.EmployeeResourceNotFoundException;
import com.example.project1.mapper.EmployeeMapper;
import com.example.project1.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;

    @Override
public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
    // Example: Call Department service to check if department is active
Long departmentId = employeeDto.getDepartmentId(); // Make sure this field exists
String departmentServiceUrl = "http://localhost:8080"; // Replace with actual Department service base URL

String url = departmentServiceUrl + "/api/departments/" + departmentId;
DepartmentDto dept = restTemplate.getForObject(url, DepartmentDto.class);
    if (dept == null || !dept.isActive()) {
        throw new RuntimeException("Cannot add employee. Department is inactive.");
    }

    Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
    Employee savedEmployee = employeeRepository.save(employee);
    return EmployeeMapper.mapToEmployeeDto(savedEmployee);
}

@Override
public List<EmployeeDto> getAllEmployees() {
    List<Employee> employees = employeeRepository.findAll();
    return employees.stream()
            .map(EmployeeMapper::mapToEmployeeDto)
            .collect(Collectors.toList());
}

@Override
public EmployeeDto getEmployeeById(Long id) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeResourceNotFoundException("Employee not found with id: " + id));
    return EmployeeMapper.mapToEmployeeDto(employee);
}

@Override
public EmployeeDto updateEmployeeById(Long id, EmployeeDto employeeDto) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeResourceNotFoundException("Employee not found with id: " + id));
    employee.setFirstName(employeeDto.getFirstName());
    employee.setLastName(employeeDto.getLastName());
    employee.setEmail(employeeDto.getEmail());
    Employee updatedEmployee = employeeRepository.save(employee);
    return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
}

@Override
public void deleteEmployeeById(Long id) {
    if (!employeeRepository.existsById(id)) {
        throw new EmployeeResourceNotFoundException("Employee not found with id: " + id);
    }
    employeeRepository.deleteById(id);
}
}