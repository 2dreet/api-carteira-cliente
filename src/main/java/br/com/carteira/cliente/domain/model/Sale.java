package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "sales")
@Data
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String status;
	
	String paymentStatus;
	
	String dueDate;
	
	Double value;
	
	Long quantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	User user;
	
	@ManyToMany(fetch = FetchType.LAZY)
	List<Customer> dependents;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
