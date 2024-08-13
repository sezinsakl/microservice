package com.project.department.repository;

import com.project.department.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();
    }

    @Test
    public void testSaveDepartment() {
        Department department = new Department();
        department.setName("HR");
        department.setLocation("Istanbul");

        Department savedDepartment = departmentRepository.save(department);

        assertThat(savedDepartment).isNotNull();
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getName()).isEqualTo("HR");
    }

    @Test
    public void testFindDepartmentById() {
        Department department = new Department();
        department.setName("Finance");
        department.setLocation("Ankara");

        Department savedDepartment = departmentRepository.save(department);

        Optional<Department> foundDepartment = departmentRepository.findById(savedDepartment.getId());

        assertThat(foundDepartment).isPresent();
        assertThat(foundDepartment.get().getName()).isEqualTo("Finance");
    }

    @Test
    public void testDeleteDepartment() {
        Department department = new Department();
        department.setName("Sales");
        department.setLocation("Ankara");

        Department savedDepartment = departmentRepository.save(department);

        departmentRepository.deleteById(savedDepartment.getId());

        Optional<Department> deletedDepartment = departmentRepository.findById(savedDepartment.getId());

        assertThat(deletedDepartment).isNotPresent();
    }
}
