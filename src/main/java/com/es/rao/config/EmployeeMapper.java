package com.es.rao.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeMapper {

	@Bean
	public ModelMapper EmployeMapper() {
		return new ModelMapper();
	}
}
