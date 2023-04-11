package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(unique = true)
	String login;

	@JsonIgnore
	String password;

	String rule;

	@ManyToOne
	Person person;
	
	@ManyToOne
	Company company;
	
	@OneToMany(mappedBy = "manager")
	List<UserBind> dependents;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
