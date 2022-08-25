package br.com.carteira.cliente.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class SaleSimpleDTO {

	Long id;

	String status;
	
	String paymentStatus;
	
	String dueDate;
	
	Double value;
	
	Long quantity;
	
	ProductDTO product;
	
	CustomerSimpleDTO customer;
	
	UserSimpleDTO user;
	
	List<CustomerSimpleDTO> customerDependents;
}
