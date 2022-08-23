package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class ProductDTO {

	Long id;

	String name;
	
	String description;
	
	Double value;
	
	String link;
	
	boolean status;

	ProductTypeDTO productType;
}
