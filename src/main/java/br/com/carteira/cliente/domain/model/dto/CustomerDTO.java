package br.com.carteira.cliente.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerDTO {
	
	Long id;

	String status;

	PersonDTO person;

	List<CustomerSimpleDTO> dependents;
}
