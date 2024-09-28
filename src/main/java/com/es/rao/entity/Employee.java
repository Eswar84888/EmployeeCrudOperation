package com.es.rao.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@Column(nullable = false, length = 50)
	@NotNull(message = "department should not be null")
	private String department;
	@Column(nullable = false, unique = true, length = 90)
	@Email(message = "complousarly providd valid email")
	@NotNull(message = "email shold not be null")
	private String email;

	@Column(nullable = false, length = 50)
	@NotNull(message = "provide valid roll Number")
	private String rollNumber;

	@Column(nullable = false, unique = true, length = 90)
	@NotNull(message = "provide valid mobile number")
	private Long phoneNumber;
	
	
	
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Address> adress;
 
	  @Column(nullable = false)
	    private boolean deleted = false;// soft delete
}
