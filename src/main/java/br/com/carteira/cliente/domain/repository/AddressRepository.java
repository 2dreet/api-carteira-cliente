package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
