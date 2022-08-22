package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class UserDTO {
	
	Long id;

	String login;

	String rule;

	PersonDTO person;

}
