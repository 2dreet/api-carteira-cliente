package br.com.carteira.cliente.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerApiRuntimeException extends RuntimeException {
	int code;
	String message;
}
