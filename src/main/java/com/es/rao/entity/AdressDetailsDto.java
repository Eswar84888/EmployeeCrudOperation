package com.es.rao.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AdressDetailsDto {
	 private String street;
	    private String city;
	    private String state;
	    private String zipCode;
	    private Address  addressType;
}
