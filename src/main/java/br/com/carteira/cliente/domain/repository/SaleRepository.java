package br.com.carteira.cliente.domain.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Sale;

public interface SaleRepository extends CrudRepository<Sale, UUID> {

//	List<Sale> findByCustomerId(Long custormerId);
//	List<Sale> findByProductId(Long productId);
//	List<Sale> findByUserId(Long userId);	
}
