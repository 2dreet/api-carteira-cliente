package br.com.carteira.cliente.util;

public class AppUtil {

	public static String onlyNumber (String string) {
		return string.replaceAll("[^0-9]", "");
	}
	
}
