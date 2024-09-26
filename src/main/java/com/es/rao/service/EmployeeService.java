package com.es.rao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.es.rao.entity.Employee;
import com.es.rao.entity.EmployeeDTO;

@Service
public interface EmployeeService {

	
	 Employee CreateEmployee(Employee emp);
	 List<Employee> CreatreEmployee(List<Employee> employee);
	 Employee getEmployee(Integer  empId);
	 List<Employee> getAllEmployee();
	 Employee updateEmployee(Integer empId, EmployeeDTO empDto);
}
