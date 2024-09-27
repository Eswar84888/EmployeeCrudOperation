package com.es.rao.Controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.es.rao.entity.Employee;
import com.es.rao.entity.EmployeeDTO;
import com.es.rao.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@PostMapping(value="/createEmp")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
		
		Employee emp=service.CreateEmployee(employee);
		return new ResponseEntity<>(emp,HttpStatus.OK);
	}

	@PostMapping(value="/createAllEmp")
	public ResponseEntity<List<Employee>> createallEmployee(@Valid @RequestBody List<Employee> employee){
		
		List<Employee> listemp=	service.CreatreEmployee(employee);
		return new ResponseEntity<>(listemp, HttpStatus.OK);	
	}
	
	@GetMapping(value="/getEmp/{empId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Integer empId){
	Employee emp;	
		emp=service.getEmployee(empId);
		return new ResponseEntity<Employee>(emp,HttpStatus.OK);
		
	}
	@GetMapping(value="/getAllEmp")
	public ResponseEntity<List<Employee>> getAllEmployee(){
	
		return new ResponseEntity<List<Employee>>(service.getAllEmployee(),HttpStatus.OK);
	}
	
	@PutMapping(value="/upadate/{empId}")
	public ResponseEntity<Employee> updateById(@PathVariable Integer empId, @RequestBody EmployeeDTO empDto){
		service.updateEmployee(empId,empDto);
		return new ResponseEntity<Employee>(service.updateEmployee(empId,empDto),HttpStatus.OK);		
	}
	
	@PutMapping(value="/updateAll")
	public ResponseEntity<List<Employee>> updateAllemp(@RequestBody List<EmployeeDTO> listemp){
	List<Employee> updateAllEmpl=service.updateAllEmployee(listemp);
		return new ResponseEntity<>(updateAllEmpl,HttpStatus.OK);
		
	}
}
