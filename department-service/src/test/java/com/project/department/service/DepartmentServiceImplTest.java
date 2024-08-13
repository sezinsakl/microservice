package com.project.department.service;

import com.project.department.constant.ErrorMessage;
import com.project.department.model.Department;
import com.project.department.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        department = new Department();
        department.setId(1L);
        department.setName("IT");
        department.setLocation("Building A");
    }

    @Test
    public void testGetAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        List<Department> departments = departmentService.getAllDepartments();

        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        assertEquals(1, departments.size());
        assertEquals(department, departments.get(0));
    }

    @Test
    public void testGetDepartmentById_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(1L);

        assertNotNull(result);
        assertEquals(department, result);
    }

    @Test
    public void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        Department result = departmentService.getDepartmentById(1L);

        assertNull(result);
    }

    @Test
    public void testAddDepartment_Success() {
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.addDepartment(department);

        assertNotNull(result);
        assertEquals(department, result);
    }

    @Test
    public void testAddDepartment_NullDepartment() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            departmentService.addDepartment(null);
        });
        assertEquals(ErrorMessage.DEPARTMENT_NULL, thrown.getMessage());
    }

    @Test
    public void testUpdateDepartment_Success() {
        Department updatedDepartment = new Department();
        updatedDepartment.setName("HR");
        updatedDepartment.setLocation("Küçükyalı");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);

        Department result = departmentService.updateDepartment(1L, updatedDepartment);

        assertNotNull(result);
        assertEquals("HR", result.getName());
        assertEquals("Küçükyalı", result.getLocation());
    }

    @Test
    public void testDeleteDepartment_Success() {
        when(departmentRepository.existsById(1L)).thenReturn(true);

        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteDepartment_NotFound() {
        when(departmentRepository.existsById(1L)).thenReturn(false);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            departmentService.deleteDepartment(1L);
        });

        assertEquals(ErrorMessage.DEPARTMENT_NOT_FOUND + 1L, thrown.getMessage());
    }
}
