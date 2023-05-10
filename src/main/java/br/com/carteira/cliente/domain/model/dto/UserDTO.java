package br.com.carteira.cliente.domain.model.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {
	
	UUID id;

	String login;

	String rule;

	PersonDTO person;
	
	CompanyDTO company;
	
	List<UserDTO> dependents;
	
	UserDTO manager;
}
