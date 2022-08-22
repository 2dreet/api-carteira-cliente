package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Document;

public interface DocumentRepository extends CrudRepository<Document, Long> {

}
