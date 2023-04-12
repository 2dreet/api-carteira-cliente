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
@Data
@Table(name = "sale_product_details")
public class SaleProductDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@OneToOne
	Product product;
	
	@OneToOne
	Sale sale;
	
	String description;
	
	Date startDate;
	
	Double value;
	
	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
