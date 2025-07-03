package com.example.project1.exception;

public class DepartmentNotFoundException extends RuntimeException {

    // Default constructor (optional)
    public DepartmentNotFoundException() {
        super("Department not found.");
    }

    // Constructor with custom message
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
