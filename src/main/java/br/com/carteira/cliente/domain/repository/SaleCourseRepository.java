package br.com.carteira.cliente.domain.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.carteira.cliente.domain.model.SaleCourse;

public interface SaleCourseRepository extends CrudRepository<SaleCourse, UUID> {

}
