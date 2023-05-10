package br.com.carteira.cliente.domain.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.CourseClassDay;

public interface CouseClassDayRepository extends CrudRepository<CourseClassDay, UUID> {

}
