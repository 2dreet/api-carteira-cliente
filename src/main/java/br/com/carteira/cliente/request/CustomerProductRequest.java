package br.com.carteira.cliente.request;

import lombok.Data;

@Data
public class CustomerProductRequest {

	String dueDate;

	long productId;
	
	long customerId;
	
	long userId;
}
