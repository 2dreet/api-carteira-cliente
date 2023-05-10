package br.com.carteira.cliente.domain.model.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CourseClassDTO {

	UUID id;

	String name;
	
	String description;
	
	String status;
	
	String link;
	
	Date startDate;
	
	CourseDTO course;
	
	List<CustomerDTO> customers;
	
	UserDTO user;
}
