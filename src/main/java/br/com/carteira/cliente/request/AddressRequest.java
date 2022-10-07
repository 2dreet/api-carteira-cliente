package br.com.carteira.cliente.request;

import br.com.carteira.cliente.enums.AddressTypeEnum;
import lombok.Data;

@Data
public class AddressRequest {
	
	Long id;
	
	String street;

	Long number;

	String neighborhood;

	String city;

	String state;

	String country;

	String zipCode;

	String adjunct;

	AddressTypeEnum type;
}
