package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, Long> {

}
