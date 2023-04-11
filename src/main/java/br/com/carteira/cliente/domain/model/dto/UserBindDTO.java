package br.com.carteira.cliente.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserBindDTO {
	
	Long id;

	String login;

	String rule;

	PersonDTO person;
	
	List<UserBindDependentDTO> dependents;
	
	List<UserBindManagerDTO> managers;
}
