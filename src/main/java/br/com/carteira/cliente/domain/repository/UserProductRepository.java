package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.UserProduct;

public interface UserProductRepository extends CrudRepository<UserProduct, Long> {

	UserProduct findByUserIdAndProductId(Long userId, Long productId);
	
}
