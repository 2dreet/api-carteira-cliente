package br.com.carteira.cliente.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, UUID> {

	@Query(value = "SELECT w.* FROM wallets w INNER JOIN wallets_users wu ON w.id = wu.wallet_id "
			+ "WHERE wu.users_id = :userId", nativeQuery = true)
	List<Wallet> findByUserId(UUID userId);

	@Query(value = "SELECT w.* FROM wallets w INNER JOIN wallets_customers wc ON w.id = wc.wallet_id "
			+ "WHERE wc.customers_id = :customerId", nativeQuery = true)
	List<Wallet> findByCustomerId(UUID customerId);

}
