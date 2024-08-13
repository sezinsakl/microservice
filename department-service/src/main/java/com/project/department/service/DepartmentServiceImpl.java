package com.project.department.service;

import com.project.department.constant.ErrorMessage;
import com.project.department.model.Department;
import com.project.department.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Department addDepartment(Department department) {
        if (department == null) {
            logger.error(ErrorMessage.DEPARTMENT_NULL);
            throw new IllegalArgumentException(ErrorMessage.DEPARTMENT_NULL);
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            department.setName(departmentDetails.getName());
            department.setLocation(departmentDetails.getLocation());
            departmentRepository.save(department);
        }else{
            logger.error(ErrorMessage.DEPARTMENT_NOT_FOUND, id);
            throw new NoSuchElementException(ErrorMessage.EMPLOYEE_NOT_FOUND + id);
        }
        return department;
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            logger.error(ErrorMessage.DEPARTMENT_NOT_FOUND, id);
            throw new NoSuchElementException(ErrorMessage.DEPARTMENT_NOT_FOUND + id);
        }
        departmentRepository.deleteById(id);
    }
}
