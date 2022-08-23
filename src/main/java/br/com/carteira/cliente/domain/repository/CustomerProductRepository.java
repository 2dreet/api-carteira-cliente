package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.CustomerProduct;

public interface CustomerProductRepository extends CrudRepository<CustomerProduct, Long> {

}
