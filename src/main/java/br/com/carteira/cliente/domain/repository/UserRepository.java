package br.com.carteira.cliente.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByLogin(String login); 
	
	List<User> findByIdIn(List<Long> ids);
}
