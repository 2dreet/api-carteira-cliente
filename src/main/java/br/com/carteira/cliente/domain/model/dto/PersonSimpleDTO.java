package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class PersonSimpleDTO {

	Long id;
	
	String name;

	String birthDate;
	
	String type;
	
	Boolean status;
	
}
