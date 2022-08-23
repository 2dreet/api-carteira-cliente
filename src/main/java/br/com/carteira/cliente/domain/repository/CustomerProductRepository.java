package br.com.carteira.cliente.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.CustomerProduct;

public interface CustomerProductRepository extends CrudRepository<CustomerProduct, Long> {

	List<CustomerProduct> findByCustomerId(Long custormerId);
	List<CustomerProduct> findByProductId(Long productId);
	
}
