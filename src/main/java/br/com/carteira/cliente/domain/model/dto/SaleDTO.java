package br.com.carteira.cliente.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class SaleDTO {

	Long id;

	String status;
	
	String paymentStatus;
	
	String dueDate;
	
	Double value;
	
	Long quantity;
	
	ProductDTO product;
	
	CustomerDTO customer;
	
	UserDTO user;
	
	List<CustomerDTO> customerDependents;
}
