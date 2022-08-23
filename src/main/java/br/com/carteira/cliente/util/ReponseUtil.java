package br.com.carteira.cliente.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ReponseUtil {

	public static <D> ResponseEntity<D> getResponse(Object source, Class<D> destinationType) {
		return getResponse(source, destinationType, HttpStatus.OK);
	}

	public static <D> ResponseEntity<D> getResponse(Object source, Class<D> destinationType, HttpStatus status) {
		return ResponseEntity.status(status).body(ClassUtil.convert(source, destinationType));
	}
}
