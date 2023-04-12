package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;

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
@Table(name = "wallets")
@Data
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String name;

	@JsonIgnore
	@ManyToMany
	List<Customer> customers;
	
	@JsonIgnore
	@ManyToMany
	List<User> users;
	
	@OneToOne
	Company company;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
