package br.com.carteira.cliente.domain.model.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class CourseClassDayDTO {

	UUID id;
	
	String description;
	
	String status;
	
	Date startDate;
	
	CourseClassDTO courseClass;
}
