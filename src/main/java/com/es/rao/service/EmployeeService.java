package com.es.rao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.es.rao.entity.Employee;

@Service
public interface EmployeeService {

	
	 Employee CreateEmployee(Employee emp);
	 List<Employee> CreatreEmployee(List<Employee> employee);
}
