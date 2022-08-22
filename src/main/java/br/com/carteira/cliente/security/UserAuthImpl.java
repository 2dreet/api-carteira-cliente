package br.com.carteira.cliente.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.repository.UserRepository;

@Component
public class UserAuthImpl implements UserDetailsService {
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuário ou Senha inválido!");
		}
		return new UserAuth(user);
	}

}
