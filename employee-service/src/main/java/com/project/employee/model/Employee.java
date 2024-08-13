package com.project.employee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "employee_registration_no", unique = true, nullable = false)
    private String name;
    private String position;
    private double salary;

    @Column(name = "department_id")
    private Long departmentId;
}
