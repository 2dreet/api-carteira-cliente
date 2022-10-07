package br.com.carteira.cliente.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.model.search.UserSearch;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByLogin(String login); 
	
	List<User> findByIdIn(List<Long> ids);
	
	List<User> findByUserManagerId(Long managerId);
	
	public User findByIdAndUserManagerId(Long userId, Long managerId);
	
	public User findByLoginAndUserManagerId(String login, Long managerId);
	
	
	@Query(value = UserSearch.sqlResult, nativeQuery = true)
	public List<User> getBySearch(String value, Long userManagerId, Integer totalByPage, Integer page);
	
	@Query(value = UserSearch.sqlCount, nativeQuery = true)
	public Integer getCountBySearch(String value, Long userManagerId);
}
