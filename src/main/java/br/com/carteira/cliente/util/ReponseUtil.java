package br.com.carteira.cliente.util;

import org.springframework.http.ResponseEntity;

public class ReponseUtil {

	public static <D> ResponseEntity<D> getResponse(Object source, Class<D> destinationType) {
		return ResponseEntity.ok().body(ClassUtil.convert(source, destinationType));
	}
}
