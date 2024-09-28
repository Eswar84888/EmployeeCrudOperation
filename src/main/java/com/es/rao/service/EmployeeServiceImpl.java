package com.es.rao.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.rao.entity.Employee;
import com.es.rao.entity.EmployeeDTO;
import com.es.rao.repo.EmployeeRepositories;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepositories repo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public Employee CreateEmployee(Employee emp) {
		return repo.save(emp);
	}

	@Override
	public List<Employee> CreatreEmployee(List<Employee> employee) {

		List<Employee> isvalidate = employee.stream().filter(this::isvalidateEmp).collect(Collectors.toList());
// 		before  saving database we have to check emails,rollnumber and phno is existing in database or not.
		for (Employee isvalidDeptnameAndEmail : isvalidate) {
			isEmailAndDepartmentUnique(isvalidDeptnameAndEmail);
		}
		return repo.saveAll(isvalidate);
	}

	private void isEmailAndDepartmentUnique(Employee isvalidDeptnameAndEmail) {
		// TODO Auto-generated method stub
		if (repo.existsByEmail(isvalidDeptnameAndEmail.getEmail())) {
			System.out.println("HI " + isvalidDeptnameAndEmail.getEmpName()
					+ " can you please try anthoer mail id,it is already exist");
		}

		if (repo.existsByRollNumber(isvalidDeptnameAndEmail.getRollNumber())) {
			System.out.println(
					"Hi " + isvalidDeptnameAndEmail.getEmpName() + " required valid rollnumber, it is already exist");
		}
		if (repo.existsByPhoneNumber(isvalidDeptnameAndEmail.getPhoneNumber())) {
			System.out.println("Hi " + isvalidDeptnameAndEmail.getEmpName()
					+ "  please check mobile number, same number already exist");
		}
	}

	public boolean isvalidateEmp(Employee employeValidaation) {
		return employeValidaation.getEmpName() != null && !employeValidaation.getEmpName().isEmpty()
				&& employeValidaation.getEmail() != null && !employeValidaation.getEmail().isEmpty()
				&& employeValidaation.getRollNumber() != null && !employeValidaation.getRollNumber().isEmpty()
				&& employeValidaation.getPhoneNumber() != null
				&& (String.valueOf(employeValidaation.getPhoneNumber()).length() == 10)
				&& employeValidaation.getDepartment() != null && !employeValidaation.getDepartment().isEmpty();
	}

	@Override
	public Employee getEmployee(Integer empId) {
		// TODO Auto-generated method stub
		validateEmployeId(empId);
		return repo.findById(empId).orElseThrow(null);
	}

	private void validateEmployeId(Integer empId) {
		// TODO Auto-generated method stub
		if (empId == null || empId <= 0) {
			System.out.println("please Enter valid empId");
		}
	}

	@Override
	public List<Employee> getAllEmployee() {
		return repo.findAll();

	}

	@Override
	public Employee updateEmployee(Integer empId, EmployeeDTO empDto) {
		// TODO Auto-generated method stub
		validateEmployeId(empId);
		// Fetch the existing employee by ID
		Employee employeeupdate = repo.findById(empId).orElseThrow(null);
		// Map properties from DTO to the existing employee
		mapper.map(empDto, employeeupdate);
		// save the update in database
		Employee updateEmpoyeeDetails = repo.save(employeeupdate);
		return updateEmpoyeeDetails;
	}

	@Override
	public List<Employee> updateAllEmployee(List<EmployeeDTO> listemp) {

		List<Employee> listupdateAll = new ArrayList<>();
		for (EmployeeDTO dto : listemp) {
			// Fetch the employee by email
			Employee existingEmployee = repo.findByEmail(dto.getEmail());

			// Check if the employee exists
			if (existingEmployee != null) {
				// Use the mapper to copy properties from DTO to entity
				mapper.map(dto, existingEmployee);
				listupdateAll.add(existingEmployee);
			} else {
				// Handle the case where the employee is not found
				throw new IllegalArgumentException("Employee not found with email: " + dto.getEmail());
			}
		}

		// Save all updated employees in a batch
		return repo.saveAll(listupdateAll);

	}

	@Transactional
	@Override
	public void deleteEmployee(Integer empId) {
		// TODO Auto-generated method stub
		validateEmployeId(empId);
		repo.deleteById(empId);

	}

	@Transactional
	@Override
	public void softdeleteEmployee(Integer empId) {
		// TODO Auto-generated method stub
		validateEmployeId(empId);
		Employee emp = repo.findById(empId).orElseThrow(null);
	emp.setDeleted(true);
	repo.save(emp);
	}

	@Override
	public void deleteAllEmployee(List<Employee> listEmp) {
		// TODO Auto-generated method stub
		for(Employee listdelete:listEmp) {
	Employee emp=	repo.findById(listdelete.getEmpId()).orElseThrow();
	emp.setDeleted(true);
	repo.save(emp);
		}
		
		
	}

}