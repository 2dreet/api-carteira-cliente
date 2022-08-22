package br.com.carteira.cliente.request;

import br.com.carteira.cliente.enums.ContactTypeEnum;
import lombok.Data;

@Data
public class ContactRequest {

	String number;
	
	ContactTypeEnum type;
}
