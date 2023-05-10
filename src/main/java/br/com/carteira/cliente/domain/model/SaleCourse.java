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
@Data
@Table(name = "sale_course")
public class SaleCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;
	
	@OneToOne
	Course course;
	
	@OneToOne
	Sale sale;
	
	String description;
	
	Date startDate;
	
	Double value;
	
	Double discount;
	
	Integer quantity;
	
	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
