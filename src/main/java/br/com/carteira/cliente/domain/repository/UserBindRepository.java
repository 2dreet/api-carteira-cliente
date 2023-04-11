package br.com.carteira.cliente.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.UserBind;

public interface UserBindRepository extends CrudRepository<UserBind, Long> {
	
	List<UserBind> findByDependentId(Long dependentId);
	
	List<UserBind> findByManagerId(Long managerId);
	
	UserBind findByDependentIdAndManagerId(Long dependentId, Long managerId);
	
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM user_bind WHERE dependent_id in (:dependentIds)", nativeQuery = true)
	void deleteAllUserBindByDependents(List<Long> dependentIds);
	
}
