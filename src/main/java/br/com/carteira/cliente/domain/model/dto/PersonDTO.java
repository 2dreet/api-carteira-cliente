package br.com.carteira.cliente.domain.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PersonDTO {

	Long id;
	
	String name;

	Date birthDate;
	
	String type;
	
	List<AddressDTO> addresses;
	
	List<ContactDTO> contacts;
	
	List<DocumentDTO> documents;
	
}
