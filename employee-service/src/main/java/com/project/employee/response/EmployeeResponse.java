package com.project.employee.response;

import com.project.employee.model.Department;
import com.project.employee.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {
    private Employee employee;
    private Department department;

}

