package br.com.carteira.cliente.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	Product findByName(String name);
	
	List<Product> findByProductTypeId(Long id);
	
}
