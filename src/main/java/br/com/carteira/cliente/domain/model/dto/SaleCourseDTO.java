package br.com.carteira.cliente.domain.model.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class SaleCourseDTO {
	
	UUID id;
	
	CourseDTO course;
	
	SaleDTO sale;
	
	String description;
	
	Date startDate;
	
	Double value;
	
	Double discount;
	
	Integer quantity;
	
}
