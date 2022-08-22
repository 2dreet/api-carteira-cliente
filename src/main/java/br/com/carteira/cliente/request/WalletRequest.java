package br.com.carteira.cliente.request;

import java.util.List;

import lombok.Data;

@Data
public class WalletRequest {
	
	String name;
	
	List<Long> usersIds;
	
	List<Long> customersIds;
}
