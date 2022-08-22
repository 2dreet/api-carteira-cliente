package br.com.carteira.cliente.handler.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.carteira.cliente.exception.ServerApiRuntimeException;
import br.com.carteira.cliente.response.RestError;

@RestControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<RestError> requestBodyInvalidException(ServerApiRuntimeException exception) {
		RestError erro = new RestError(exception.getCode(), exception.getMessage());
		return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
	}
}
