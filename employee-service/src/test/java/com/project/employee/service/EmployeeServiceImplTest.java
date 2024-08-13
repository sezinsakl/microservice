package com.project.employee.service;

import com.project.employee.client.DepartmentClient;
import com.project.employee.constant.ErrorMessage;
import com.project.employee.model.Department;
import com.project.employee.model.Employee;
import com.project.employee.repository.EmployeeRepository;
import com.project.employee.response.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentClient departmentClient;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private Department department;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        initializeTestData();
    }

    private void initializeTestData() {
        employee = new Employee();
        employee.setId(1L);
        employee.setName("Sezin Şaklıoğlu");
        employee.setPosition("Expert Developer");
        employee.setSalary(100000.0);
        employee.setDepartmentId(1L);

        department = new Department();
        department.setId(1L);
        department.setName("IT");
        department.setLocation("Küçükyalı");
    }

    @Test
    public void testGetEmployeeById_Success() throws Exception {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentClient.getDepartmentById(1L)).thenReturn(department);

        EmployeeResponse response = employeeService.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(employee, response.getEmployee());
        assertEquals(department, response.getDepartment());
    }

    @Test
    public void testGetEmployeeById_EmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            employeeService.getEmployeeById(1L);
        });

        assertEquals(ErrorMessage.EMPLOYEE_NOT_FOUND + 1L, thrown.getMessage());
    }

    @Test
    public void testGetEmployeeById_DepartmentNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentClient.getDepartmentById(1L)).thenReturn(null);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            employeeService.getEmployeeById(1L);
        });

        assertEquals(ErrorMessage.DEPARTMENT_NOT_FOUND + 1L, thrown.getMessage());
    }

    @Test
    public void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(employee, employees.get(0));
    }

    @Test
    public void testAddEmployee_Success() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.addEmployee(employee);

        assertNotNull(result);
        assertEquals(employee, result);
        verify(employeeRepository).save(employee);
    }

    @Test
    public void testAddEmployee_NullEmployee() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            employeeService.addEmployee(null);
        });

        assertEquals(ErrorMessage.EMPLOYEE_NULL, thrown.getMessage());
    }

    @Test
    public void testUpdateEmployee_Success() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        employee.setName("Sezin Şaklıoğlu");
        Employee updatedEmployee = employeeService.updateEmployee(1L, employee);

        assertNotNull(updatedEmployee);
        assertEquals("Sezin Şaklıoğlu", updatedEmployee.getName());
    }

    @Test
    public void testUpdateEmployee_NotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            employeeService.updateEmployee(1L, employee);
        });

        assertEquals(ErrorMessage.EMPLOYEE_NOT_FOUND + 1L, thrown.getMessage());
    }

    @Test
    public void testDeleteEmployee_Success() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    public void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            employeeService.deleteEmployee(1L);
        });

        assertEquals(ErrorMessage.EMPLOYEE_NOT_FOUND + 1L, thrown.getMessage());
    }
}
