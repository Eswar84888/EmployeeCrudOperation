package com.es.rao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Adress")
public class Address  {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String street;
	    private String city;
	    private String state;
	    private String zipCode;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private AddressType addressType; // Use the correct AddressType enum

	    // Relationships
	    @ManyToOne
	    @JoinColumn(name = "employee_id", nullable = false)
	    @JsonIgnore
	    private Employee employee;
	    
	    
	   
}
