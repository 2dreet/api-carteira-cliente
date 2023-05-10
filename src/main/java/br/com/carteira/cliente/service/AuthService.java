package br.com.carteira.cliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.enums.UserRuleEnum;
import br.com.carteira.cliente.exception.ServerApiRuntimeException;

@Service
public class AuthService {

	@Autowired
	UserService userService;

	public void verify(UserRuleEnum[] rules) {
		User user = userService.getUserInContext();
		if (user != null && rules != null && rules.length > 0) {
			if (!rules.toString().contains(user.getRule())) {
				throw new ServerApiRuntimeException(404, "Usuário sem permissão para acessar esse conteúdo!");
			}
		}
	}

}
