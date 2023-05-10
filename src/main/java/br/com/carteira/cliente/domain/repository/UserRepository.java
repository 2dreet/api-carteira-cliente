package br.com.carteira.cliente.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.repository.search.UserSearch;

public interface UserRepository extends CrudRepository<User, UUID> {
	
	public User findByLogin(String login); 
	
//	List<User> findByIdIn(List<Long> ids);
	
//	List<User> findByUserAdminId(Long managerId);
	
//	public User findByIdAndUserAdminId(Long userId, Long managerId);
	
//	public User findByLoginAndUserAdminId(String login, Long managerId);
	
	@Query(value = UserSearch.sqlResult, nativeQuery = true)
	public List<User> getBySearch(String value, Long userAdminId, Integer totalByPage, Integer page);
	
	@Query(value = UserSearch.sqlCount, nativeQuery = true)
	public Integer getCountBySearch(String value, Long userAdminId);
}
