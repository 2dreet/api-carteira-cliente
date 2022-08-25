package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class UserSimpleDTO {
	
	Long id;

	String login;

	String rule;

	PersonSimpleDTO person;

}
