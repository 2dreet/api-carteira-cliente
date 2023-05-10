package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "course_class")
@Data
public class CourseClass {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;

	String name;
	
	String description;
	
	String status;
	
	String link;
	
	Date startDate;
	
	@OneToOne
	Course course;
	
	@JsonIgnore
	@ManyToMany
	List<Customer> customers;
	
	@OneToOne
	User user;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
