package br.com.carteira.cliente.domain.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Company;

public interface CompanyRepository extends CrudRepository<Company, UUID> {

}
