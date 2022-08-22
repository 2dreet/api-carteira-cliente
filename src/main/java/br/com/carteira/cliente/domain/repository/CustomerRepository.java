package br.com.carteira.cliente.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByIdIn(List<Long> ids);
	
}
