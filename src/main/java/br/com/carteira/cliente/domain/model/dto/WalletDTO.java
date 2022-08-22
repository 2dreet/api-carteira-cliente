package br.com.carteira.cliente.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class WalletDTO {
	
	Long id;
	
	String name;
	
	List<CustomerDTO> customers;
	
	List<UserDTO> users;
	
}
