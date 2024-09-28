package com.es.rao.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class EmployeeDTO {

	
	private String empName;
	private String department;
	private String email;
	private Long phoneNumber;
	 private List<AdressDetailsDto> addresses; // List of addresses
}
