package com.project.department.service;


import com.project.department.model.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Department addDepartment(Department department);

    Department updateDepartment(Long id, Department departmentDetails);

    void deleteDepartment(Long id);
}