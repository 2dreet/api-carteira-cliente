package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;

	@Column(unique = true)
	String login;

	@JsonIgnore
	String password;

	String rule;

	@OneToOne
	Person person;
	
	@OneToOne
	Company company;
	
	Double commissionPercentage;
	
	String status;
	
	@OneToMany(mappedBy = "manager")
	List<User> dependents;
	
	@OneToOne
	User manager;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
