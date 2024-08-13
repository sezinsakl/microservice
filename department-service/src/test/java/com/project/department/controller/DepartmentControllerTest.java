package com.project.department.controller;

import com.project.department.model.Department;
import com.project.department.service.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentServiceImpl departmentServiceImpl;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testGetAllDepartments_Success() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");
        department.setLocation("Ankara");

        when(departmentServiceImpl.getAllDepartments()).thenReturn(Collections.singletonList(department));

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("HR"));
    }

    @Test
    public void testGetDepartmentById_Success() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");
        department.setLocation("Ankara");

        when(departmentServiceImpl.getDepartmentById(anyLong())).thenReturn(department);

        mockMvc.perform(get("/departments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    public void testAddDepartment_Success() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR");

        when(departmentServiceImpl.addDepartment(any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/departments")
                        .contentType("application/json")
                        .content("{\"name\":\"HR\",\"location\":\"Ankara\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("HR"));
    }
    
    @Test
    public void testUpdateDepartment_Success() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("HR Updated");

        when(departmentServiceImpl.updateDepartment(anyLong(), any(Department.class))).thenReturn(department);

        mockMvc.perform(put("/departments/{id}", 1L)
                        .contentType("application/json")
                        .content("{\"name\":\"HR Updated\",\"location\":\"Istanbul\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("HR Updated"));
    }
    
}
