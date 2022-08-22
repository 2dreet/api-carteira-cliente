package br.com.carteira.cliente.request;

import br.com.carteira.cliente.enums.DocumentTypeEnum;
import lombok.Data;

@Data
public class DocumentRequest {
	
	String number;

	DocumentTypeEnum type;
}
