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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RestTemplate restTemplate;

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.trace("TRACE: Entered createNewEmployee in service");
        log.debug("DEBUG: EmployeeDto received: {}", employeeDto);
        Long departmentId = employeeDto.getDepartmentId();
        String departmentServiceUrl = "http://localhost:8080";
        String url = departmentServiceUrl + "/api/departments/" + departmentId;
        log.info("INFO: Checking department status for departmentId: {}", departmentId);
        DepartmentDto dept = restTemplate.getForObject(url, DepartmentDto.class);
        if (dept == null || !dept.isActive()) {
            log.warn("WARN: Department with ID {} is inactive or not found", departmentId);
            throw new RuntimeException("Cannot add employee. Department is inactive.");
        }
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("INFO: Employee created successfully: {}", savedEmployee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }


    @Override
    public List<EmployeeDto> getAllEmployees() {
        log.trace("TRACE: Entered getAllEmployees in service");
        List<Employee> employees = employeeRepository.findAll();
        log.debug("DEBUG: Number of employees found: {}", employees.size());
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        log.trace("TRACE: Entered getEmployeeById in service");
        log.debug("DEBUG: Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("WARN: Employee with ID {} not found", id);
                    return new EmployeeResourceNotFoundException("Employee not found with id: " + id);
                });
        log.info("INFO: Employee found: {}", employee);
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployeeById(Long id, EmployeeDto employeeDto) {
        log.trace("TRACE: Entered updateEmployeeById in service");
        log.debug("DEBUG: Updating employee with ID: {}, Data: {}", id, employeeDto);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("WARN: Employee with ID {} not found for update", id);
                    return new EmployeeResourceNotFoundException("Employee not found with id: " + id);
                });
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("INFO: Employee updated successfully: {}", updatedEmployee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        log.trace("TRACE: Entered deleteEmployeeById in service");
        if (!employeeRepository.existsById(id)) {
            log.warn("WARN: Employee with ID {} not found for deletion", id);
            throw new EmployeeResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
        log.info("INFO: Employee with ID {} deleted successfully", id);
    }
}