package com.example.project1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project1.dto.EmployeeDto;
import com.example.project1.service.EmployeeServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

   @PostMapping
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto employeeDto) {
        log.trace("TRACE: Entered createNewEmployee method");
        log.debug("DEBUG: Received employeeDto: {}", employeeDto);
        log.info("INFO: Creating new employee");
        try {
            EmployeeDto savedEmployee = employeeService.createNewEmployee(employeeDto);
            log.info("INFO: Employee created successfully: {}", savedEmployee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("ERROR: Exception while creating employee", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId) {
        log.trace("TRACE: Entered getEmployeeById method");
        log.debug("DEBUG: Fetching employee with ID: {}", employeeId);
        EmployeeDto employeeById = employeeService.getEmployeeById(employeeId);
        if (employeeById == null) {
            log.warn("WARN: Employee with ID {} not found", employeeId);
        } else {
            log.info("INFO: Employee found: {}", employeeById);
        }
        return new ResponseEntity<>(employeeById, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long employeeId) {
        log.trace("TRACE: Entered deleteEmployeeById method");
        log.debug("DEBUG: Deleting employee with ID: {}", employeeId);
        try {
            employeeService.deleteEmployeeById(employeeId);
            log.warn("WARN: Employee with ID {} deleted", employeeId);
            return new ResponseEntity<>("Employee Record Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR: Exception while deleting employee", e);
            return new ResponseEntity<>("Error deleting employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}// added some comments to the code
// This is the EmployeeController class that handles HTTP requests related to Employee operations.
// It uses Spring's RestController and RequestMapping annotations to define the base URL for the employee endpoints.