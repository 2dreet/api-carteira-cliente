package br.com.carteira.cliente.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyInvalidException extends ServerApiRuntimeException {

	int code;
	String message;
	
}
