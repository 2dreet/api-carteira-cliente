package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.PersonProduct;

public interface PersonProductRepository extends CrudRepository<PersonProduct, Long> {

}
