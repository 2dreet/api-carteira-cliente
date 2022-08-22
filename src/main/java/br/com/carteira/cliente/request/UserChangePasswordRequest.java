package br.com.carteira.cliente.request;

import lombok.Data;

@Data
public class UserChangePasswordRequest {

	String oldPassword, newPassword;
	
}
