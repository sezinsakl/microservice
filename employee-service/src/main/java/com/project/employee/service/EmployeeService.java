package com.project.employee.service;

import com.project.employee.model.Employee;
import com.project.employee.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse getEmployeeById(Long id) throws Exception;

    List<Employee> getAllEmployees();

    Employee addEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);
}

