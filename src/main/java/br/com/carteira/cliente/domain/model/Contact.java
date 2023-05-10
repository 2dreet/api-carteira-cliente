package br.com.carteira.cliente.domain.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "contact")
@Data
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	UUID id;

	String number;

	String type;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	Person person;

	@CreationTimestamp
	Date createAt;

	@UpdateTimestamp
	Date updateAt;
}
