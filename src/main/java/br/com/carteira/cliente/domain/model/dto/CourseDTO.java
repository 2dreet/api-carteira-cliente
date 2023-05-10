package br.com.carteira.cliente.domain.model.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CourseDTO {

	UUID id;

	String name;
	
	String description;
	
	Double value;
	
	String status;
	
	String courseType;
	
	CompanyDTO company;
}
