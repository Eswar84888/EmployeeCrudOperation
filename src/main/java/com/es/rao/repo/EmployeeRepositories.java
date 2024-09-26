package com.es.rao.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.rao.entity.Employee;

@Repository
public interface EmployeeRepositories extends JpaRepository<Employee,Integer > {
	boolean existsByEmail(String email); 
	boolean existsByRollNumber(String rollNumber) ;
	boolean existsByPhoneNumber(Long phoneNumber) ;
	}

