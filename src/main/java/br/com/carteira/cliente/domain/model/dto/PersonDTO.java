package br.com.carteira.cliente.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class PersonDTO {

	Long id;
	
	String name;

	String birthDate;
	
	String type;
	
	Boolean status;
	
	List<AddressDTO> addresses;
	
	List<ContactDTO> contacts;
	
	List<DocumentDTO> documents;
	
}
