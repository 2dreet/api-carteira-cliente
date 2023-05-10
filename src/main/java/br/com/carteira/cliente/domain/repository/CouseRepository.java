package br.com.carteira.cliente.domain.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.Course;

public interface CouseRepository extends CrudRepository<Course, UUID> {

}
