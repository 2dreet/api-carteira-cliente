package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class ProductDTO {

	Long id;

	String name;
	
	Double value;
	
	boolean status;

	ProductTypeDTO productType;
}
