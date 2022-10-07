package br.com.carteira.cliente.request;

import java.util.List;

import lombok.Data;

@Data
public class PersonRequest {

	Long id;
	
	String name;
	
	String email;

	String birthDate;
	
	List<AddressRequest> addresses;
	
	List<ContactRequest> contacts;

	List<DocumentRequest> documents;
	
}
