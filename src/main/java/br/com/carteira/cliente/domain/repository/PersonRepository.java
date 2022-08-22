package br.com.carteira.cliente.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findByIdIn(List<Long> ids);
	
}
