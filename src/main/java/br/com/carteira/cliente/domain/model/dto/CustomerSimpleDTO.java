package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class CustomerSimpleDTO {
	
	Long id;

	String status;

	PersonSimpleDTO person;
}
