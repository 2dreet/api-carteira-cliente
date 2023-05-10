package br.com.carteira.cliente.domain.model.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class PersonDTO {

	UUID id;
	
	String name;

	String birthDate;
	
	String email;
	
	List<AddressDTO> addresses;
	
	List<ContactDTO> contacts;
	
	List<DocumentDTO> documents;
	
}
