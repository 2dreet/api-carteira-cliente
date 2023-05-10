package br.com.carteira.cliente.domain.model.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class SaleDTO {

	UUID id;

	String status;
	
	Date dueDate;
	
	Double sellerCommissionPercentage;
	
	List<SaleCourseDTO> saleCourses;
	
	CustomerDTO customer;
	
	CompanyDTO company;
	
	UserDTO seller;
	
	Date createAt;
}
