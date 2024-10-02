package com.es.rao.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class EmployeeMapper {

	@Bean
	public ModelMapper EmployeMapper() {
		return new ModelMapper();
	}


}
