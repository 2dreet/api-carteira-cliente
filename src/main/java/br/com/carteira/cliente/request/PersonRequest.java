package br.com.carteira.cliente.request;

import java.util.List;

import br.com.carteira.cliente.enums.PersonTypeEnum;
import lombok.Data;

@Data
public class PersonRequest {

	String name;
	
	String email;

	String birthDate;
	
	PersonTypeEnum type;
	
	List<AddressRequest> addresses;
	
	List<ContactRequest> contacts;

	List<DocumentRequest> documents;
	
}
