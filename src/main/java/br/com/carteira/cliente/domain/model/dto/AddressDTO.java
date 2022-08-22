package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class AddressDTO {
	
	Long id;
	
	String street;

	Long number;

	String neighborhood;

	String city;

	String state;

	String country;

	String zipCode;

	String adjunct;

	String type;
}
