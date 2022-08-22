package br.com.carteira.cliente.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "person_product")
@Data
public class PersonProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String status;
	
	String paymentStatus;
	
	String dueDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	Product product;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	Person person;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
