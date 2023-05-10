package br.com.carteira.cliente.domain.model.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CustomerDTO {
	
	UUID id;

	String status;

	PersonDTO person;
	
	CompanyDTO company;

}
