package com.es.rao.entity;

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
	    private Address addressType; // Permanent or Temporary
	 // Relationships
	    @ManyToOne
	    @JoinColumn(name = "empId", nullable = false)
	    private Employee employee;
	    
	    
	    
	    
	    enum AddressType {
	        PERMANENT,
	        TEMPORARY
	    }
}
