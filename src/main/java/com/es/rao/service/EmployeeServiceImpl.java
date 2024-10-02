package com.es.rao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.rao.entity.Address;
import com.es.rao.entity.AddressDto;
import com.es.rao.entity.Employee;
import com.es.rao.entity.EmployeeDTO;
import com.es.rao.repo.AddressRepository;
import com.es.rao.repo.EmployeeRepositories;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepositories repo;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AddressRepository addressRepo;

	@Override
	public Employee CreateEmployee(EmployeeDTO employeeDto) {

		Employee employee = mapper.map(employeeDto, Employee.class);

		// Map Addresses from DTO to Entity
		List<Address> addresses = employeeDto.getAddressList().stream().map(addressDto -> {
			Address address = mapper.map(addressDto, Address.class);
			// Set back-reference
			address.setEmployee(employee);

			return address;
		}).collect(Collectors.toList());

		employee.setAddressList(addresses);

		return repo.save(employee);
	}

	// Save the Employee

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
		// Step 1: Fetch the existing employee by ID (and its addresses)
		Employee existingEmployee = repo.findById(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + empId));
		updateExistingEmployee(empDto, existingEmployee);
		// Step 3: Handle the address list
		List<Address> existingAddresses = existingEmployee.getAddressList(); // Existing addresses from DB
		List<AddressDto> addressDtoList = empDto.getAddressList(); // Incoming addresses from DTO
		System.out.println(addressDtoList.size());
		// Step 4: Update or add addresses by index

		for (int i = 0; i < addressDtoList.size(); i++) {
			AddressDto addressDto = addressDtoList.get(i);
			if (existingAddresses.size() >= i) {
				Address address = existingAddresses.get(i);
				updateExistingAddresses(addressDto, address);

			} else {
				// if employee adress data is not exist then can update new data
				addNewAddressIfNotPresent(addressDto);
			}
		}

		// Step 6: Save the updated employee
		return repo.save(existingEmployee);
	}

	private void updateExistingAddresses(AddressDto addressDto, Address address) {
		// TODO Auto-generated method stub

		if (addressDto.getZipCode() != null) {
			address.setZipCode(addressDto.getZipCode());
		}

		if (addressDto.getStreet() != null) {
			address.setStreet(addressDto.getStreet());
		}
		if (addressDto.getState() != null) {
			address.setState(addressDto.getState());
		}

		if (addressDto.getCity() != null) {
			address.setCity(addressDto.getCity());
		}
		if (addressDto.getAddressType() != null) {
			address.setAddressType(addressDto.getAddressType());
		}
	}

	private void updateExistingEmployee(EmployeeDTO empDto, Employee existingEmployee) {
		// TODO Auto-generated method stub
		if (empDto.getEmpName() != null) {
			existingEmployee.setEmpName(empDto.getEmpName());
		}
		if (empDto.getEmail() != null) {
			existingEmployee.setEmail(empDto.getEmail());
		}
		if (empDto.getPhoneNumber() != null) {
			existingEmployee.setPhoneNumber(empDto.getPhoneNumber());
		}

		if (empDto.getDepartment() != null) {
			existingEmployee.setDepartment(empDto.getDepartment());
		}
	}

	@Override
	public List<Employee> updateAllEmployee(List<EmployeeDTO> listemp) {
		List<Employee> listupdateAll = new ArrayList<>();
		for (EmployeeDTO empDto : listemp) {
			Employee existingEmployee = repo.findByEmail(empDto.getEmail());
			updateExistingEmployee(empDto, existingEmployee);
			// Step 3: Handle the address list
			List<Address> existingAddresses = existingEmployee.getAddressList(); // Existing addresses from DB
			List<AddressDto> addressDtoList = empDto.getAddressList(); // Incoming addresses from DTO
			int i;
			// Step 4: Update or add addresses by index
			for (i = 0; i < addressDtoList.size(); i++) {
				AddressDto addressDto = addressDtoList.get(i);
				if (existingAddresses.size() > i) {
					Address address = existingAddresses.get(i);
					updateExistingAddresses(addressDto, address);
				}

				else {
					// if employee adress data is not exist then can update new data
					addNewAddressIfNotPresent(addressDto);
				}
			}
			// Add employee to the list of employees to be updated
			listupdateAll.add(existingEmployee);
		}

		// Save all updated employees in a batch
		return repo.saveAll(listupdateAll);
	}

	private void addNewAddressIfNotPresent(AddressDto addressDto) {
		// TODO Auto-generated method stub
		Address addressnew = new Address();
		addressnew.setZipCode(addressDto.getZipCode());
		addressnew.setStreet(addressDto.getStreet());
		addressnew.setState(addressDto.getState());
		addressnew.setAddressType(addressDto.getAddressType());
		addressnew.setCity(addressDto.getCity());
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
		for (Employee listdelete : listEmp) {
			Employee emp = repo.findById(listdelete.getEmpId()).orElseThrow();
			emp.setDeleted(true);
			repo.save(emp);
		}

	}

}