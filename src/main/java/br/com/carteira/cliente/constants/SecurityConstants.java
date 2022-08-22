package br.com.carteira.cliente.constants;

public class SecurityConstants {
	public static final String SECRET = "SECRET_KEY";
	public static final long EXPIRATION_TIME = 36000000; // 10 horas
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
}
