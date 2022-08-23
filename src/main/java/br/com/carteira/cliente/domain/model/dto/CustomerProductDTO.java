package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class CustomerProductDTO {

	Long id;

	String status;
	
	String paymentStatus;
	
	String productStatus;
	
	String dueDate;
	
	ProductDTO product;
	
	CustomerDTO customer;
	
	UserDTO user;
}
