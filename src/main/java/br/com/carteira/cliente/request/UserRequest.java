package br.com.carteira.cliente.request;

import br.com.carteira.cliente.enums.UserRuleEnum;
import lombok.Data;

@Data
public class UserRequest {

	String login, password;
	
	PersonRequest person;
	
	UserRuleEnum rule;
}
