package br.com.carteira.cliente.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.carteira.cliente.response.RestError;

public class ReponseUtil {

	public static <D> ResponseEntity<D> getResponse(Object source, Class<D> destinationType) {
		return getResponse(source, destinationType, HttpStatus.OK);
	}

	public static <D> ResponseEntity<D> getResponse(Object source, Class<D> destinationType, HttpStatus status) {
		return ResponseEntity.status(status).body(ClassUtil.convert(source, destinationType));
	}
	
	public static void sendResponseErroAuthenticate(HttpServletRequest req, HttpServletResponse res, RestError re) throws IOException {
		SecurityContextHolder.clearContext();
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(re.getCode());
        OutputStream responseStream = res.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, re);
        responseStream.flush();
	}
}
