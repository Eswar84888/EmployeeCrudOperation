package com.es.rao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.rao.entity.Address;
import com.es.rao.entity.Employee;
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
	
}
