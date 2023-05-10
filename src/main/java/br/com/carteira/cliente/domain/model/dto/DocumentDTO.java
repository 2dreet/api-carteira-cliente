package br.com.carteira.cliente.domain.model.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DocumentDTO {

	UUID id;
	
	String number;
	
	String type;
}
