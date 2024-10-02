package com.es.rao.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;

    @NotNull(message = "name should not be null")
    @Column(nullable = false, length = 50)
    private String empName;

    @NotNull(message = "department should not be null")
    @Column(nullable = false, length = 50)
    private String department;

    @Email(message = "provide valid email")
    @NotNull(message = "email should not be null")
    @Column(nullable = false, unique = true, length = 90)
    private String email;

    @NotNull(message = "provide valid roll Number")
    @Column(nullable = false, length = 50)
    private String rollNumber;

    @NotNull(message = "provide valid mobile number")
    @Column(nullable = false, unique = true, length = 90)
    private Long phoneNumber;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>(); // Renamed for clarity

    @Column(nullable = false)
    private boolean deleted = false; // For soft delete
}
