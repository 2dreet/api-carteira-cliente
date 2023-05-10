package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;

	String name;
	
	String email;

	String birthDate;

	@OneToMany(mappedBy = "person")
	List<Address> addresses;

	@OneToMany(mappedBy = "person")
	List<Contact> contacts;
	
	@OneToMany(mappedBy = "person")
	List<Document> documents;
	
	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
