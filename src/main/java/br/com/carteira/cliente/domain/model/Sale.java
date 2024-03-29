package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
@Table(name = "sale")
@Data
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;

	String status;
	
	Date dueDate;
	
	Double sellerCommissionPercentage;
	
	@OneToMany(mappedBy = "sale")
	List<SaleCourse> saleCourses;
	
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
