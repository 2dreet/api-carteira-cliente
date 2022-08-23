package br.com.carteira.cliente.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.UserProduct;

public interface UserProductRepository extends CrudRepository<UserProduct, Long> {

	List<UserProduct> findByUserId(Long userId);
	
	List<UserProduct> findByProductId(Long productId);
	
	UserProduct findByUserIdAndProductId(Long userId, Long productId);
	
}
