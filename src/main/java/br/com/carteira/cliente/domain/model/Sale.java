package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@OneToMany(mappedBy = "sale")
	List<SaleProductDetail> productDetails;
	
	@OneToOne
	Customer customer;
	
	@OneToOne
	Company company;
	
	@OneToOne
	User seller;
	
	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
