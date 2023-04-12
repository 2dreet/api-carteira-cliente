package br.com.carteira.cliente.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findByIdIn(List<Long> ids);
	
//	List<Customer> findByResponsibleId(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE customers SET responsible_id = null WHERE responsible_id = :responsibleId", nativeQuery = true)
	void removeResponsible(Long responsibleId);
}
