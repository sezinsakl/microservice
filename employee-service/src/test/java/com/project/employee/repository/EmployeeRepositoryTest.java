package com.project.employee.repository;

import com.project.employee.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setName("Ahmet");
        employee.setPosition("Developer");
        employee.setSalary(75000);
        employee.setDepartmentId(1L);

        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
        assertThat(savedEmployee.getName()).isEqualTo("Ahmet");
    }

    @Test
    public void testFindEmployeeById() {
        Employee employee = new Employee();
        employee.setName("Ayşe");
        employee.setPosition("Manager");
        employee.setSalary(85000);
        employee.setDepartmentId(2L);

        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getId());

        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getName()).isEqualTo("Ayşe");
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setName("Elif");
        employee.setPosition("Analyst");
        employee.setSalary(65000);
        employee.setDepartmentId(3L);

        Employee savedEmployee = employeeRepository.save(employee);

        employeeRepository.deleteById(savedEmployee.getId());

        Optional<Employee> deletedEmployee = employeeRepository.findById(savedEmployee.getId());

        assertThat(deletedEmployee).isNotPresent();
    }
}
