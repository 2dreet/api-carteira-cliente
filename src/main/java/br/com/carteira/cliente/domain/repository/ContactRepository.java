package br.com.carteira.cliente.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long> {

}
