package com.example.project1.exception;

public class DepartmentInactiveException  extends RuntimeException {
    public DepartmentInactiveException(String message) {
        super(message);
    }
}