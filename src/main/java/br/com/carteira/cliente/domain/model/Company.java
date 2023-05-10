package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;
	
	String name;
	
	String cnpj;
	
	String representativeCpf;
		
	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
	
}
