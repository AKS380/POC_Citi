package com.example.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long deptId;

    public Long getDepartmentId() {
        return deptId;
    }

    public void setDepartmentId(Long departmentId) {
        this.deptId = departmentId;
    }

   
}
