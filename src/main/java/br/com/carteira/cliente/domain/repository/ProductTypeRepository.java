package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.ProductType;

public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {

	ProductType findByName(String name);
	
}
