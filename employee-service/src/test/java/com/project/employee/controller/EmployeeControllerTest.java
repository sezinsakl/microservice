package com.project.employee.controller;

import com.project.employee.model.Employee;
import com.project.employee.response.EmployeeResponse;
import com.project.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testGetEmployeeById_Success() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Sezin");
        employee.setPosition("Developer");
        employee.setSalary(100000.0);
        employee.setDepartmentId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setEmployee(employee);
        response.setDepartment(null);

        when(employeeService.getEmployeeById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/employees/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee.id").value(1L))
                .andExpect(jsonPath("$.employee.name").value("Sezin"));
    }

    @Test
    public void testGetEmployeeById_NotFound() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenThrow(new NoSuchElementException("Employee not found with id: 1"));

        mockMvc.perform(get("/employees/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Sezin");

        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employee));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Sezin"));
    }

    @Test
    public void testAddEmployee_Success() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Sezin");

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType("application/json")
                        .content("{\"name\":\"Sezin\",\"position\":\"Developer\",\"salary\":100000,\"departmentId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Sezin"));
    }

    @Test
    public void testAddEmployee_BadRequest() throws Exception {
        when(employeeService.addEmployee(any(Employee.class))).thenThrow(new IllegalArgumentException("Employee cannot be null"));

        mockMvc.perform(post("/employees")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEmployee_Success() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Sezin");

        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/employees/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"name\":\"Sezin Updated\",\"position\":\"Developer\",\"salary\":110000,\"departmentId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateEmployee_NotFound() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenThrow(new NoSuchElementException("Employee not found with id: 1"));

        mockMvc.perform(put("/employees/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"name\":\"Sezin\",\"position\":\"Developer\",\"salary\":100000,\"departmentId\":1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteEmployee_Success() throws Exception {
        doNothing().when(employeeService).deleteEmployee(anyLong());

        mockMvc.perform(delete("/employees/{id}", 1L));
    }
}
           
