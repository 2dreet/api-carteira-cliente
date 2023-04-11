package br.com.carteira.cliente.domain.model;

import java.util.Date;

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
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String name;
	
	String code;
		
	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
	
}
