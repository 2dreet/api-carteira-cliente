package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class ContactDTO {

	Long id;
	
	String number;
	
	String type;
}
