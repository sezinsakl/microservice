package com.project.employee.service;

import com.project.employee.client.DepartmentClient;
import com.project.employee.constant.ErrorMessage;
import com.project.employee.model.Department;
import com.project.employee.model.Employee;
import com.project.employee.repository.EmployeeRepository;
import com.project.employee.response.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentClient departmentClient;

    @Override
    public EmployeeResponse getEmployeeById(Long id) throws Exception{
        logger.debug("Fetching employee with ID: {}", id);
        EmployeeResponse response = new EmployeeResponse();
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(ErrorMessage.EMPLOYEE_NOT_FOUND + id));
            response.setEmployee(employee);
            Department department = departmentClient.getDepartmentById(employee.getDepartmentId());
            if (department == null) {
                throw new NoSuchElementException(ErrorMessage.DEPARTMENT_NOT_FOUND + id);
            } else {
                response.setDepartment(department);
            }
        } catch (Exception e) {
            logger.error(ErrorMessage.EMPLOYEE_SERVICE_ERROR + e.getMessage());
            throw e;
        }
        return response;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        if (employee == null) {
            logger.error(ErrorMessage.EMPLOYEE_NULL);
            throw new IllegalArgumentException(ErrorMessage.EMPLOYEE_NULL);
        }
        logger.info("Adding new employee with name: ", employee.getName());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        if (!employeeRepository.existsById(id)) {
            logger.error(ErrorMessage.EMPLOYEE_NOT_FOUND, id);
            throw new NoSuchElementException(ErrorMessage.EMPLOYEE_NOT_FOUND + id);
        }
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setName(employeeDetails.getName());
            employee.setPosition(employeeDetails.getPosition());
            employee.setSalary(employeeDetails.getSalary());
            return employeeRepository.save(employee);
        }
        return employeeDetails;
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            logger.error(ErrorMessage.EMPLOYEE_DELETE_ERROR + id);
            throw new NoSuchElementException(ErrorMessage.EMPLOYEE_NOT_FOUND + id);
        }
        employeeRepository.deleteById(id);
    }
}


