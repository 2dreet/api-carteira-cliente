package br.com.carteira.cliente.domain.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.CourseClass;

public interface CouseClassRepository extends CrudRepository<CourseClass, UUID> {

}
