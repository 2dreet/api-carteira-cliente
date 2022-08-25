package br.com.carteira.cliente.domain.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class WalletSimpleDTO {
	
	Long id;
	
	String name;
	
	List<CustomerSimpleDTO> customers;
	
	List<UserSimpleDTO> users;
	
}
