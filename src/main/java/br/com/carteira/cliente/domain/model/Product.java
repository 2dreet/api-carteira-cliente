package br.com.carteira.cliente.domain.model;

import java.util.Date;

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
@Table(name = "products")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String name;
	
	String description;
	
	Double value;
	
	boolean status;
	
	String link;
	
	@OneToOne
	Company company;

	@OneToOne
	ProductType productType;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
