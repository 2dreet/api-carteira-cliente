package br.com.carteira.cliente.domain.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {

//	List<Customer> findByIdIn(List<UUID> ids);
	
//	List<Customer> findByResponsibleId(Long id);
	
//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE customer SET responsible_id = null WHERE responsible_id = :responsibleId", nativeQuery = true)
//	void removeResponsible(Long responsibleId);
}
