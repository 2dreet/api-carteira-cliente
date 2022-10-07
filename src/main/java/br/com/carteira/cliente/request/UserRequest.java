package br.com.carteira.cliente.request;

import br.com.carteira.cliente.enums.UserRuleEnum;
import lombok.Data;

@Data
public class UserRequest {

	Long id;
	
	String login;
	
	PersonRequest person;
	
	UserRuleEnum rule;
	
}
