package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;

	String name;
	
	String description;
	
	Double value;
	
	String status;
	
	String courseType;
	
	@OneToOne
	Company company;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
