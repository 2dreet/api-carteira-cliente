package br.com.carteira.cliente.request;

import lombok.Data;

@Data
public class UserAuthRequest {
	String login, password, cnpj;
}
