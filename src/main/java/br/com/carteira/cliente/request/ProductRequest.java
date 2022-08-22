package br.com.carteira.cliente.request;

import lombok.Data;

@Data
public class ProductRequest {
	
	String name;
	
	Double value;
	
	Long productTypeId;
	
}
