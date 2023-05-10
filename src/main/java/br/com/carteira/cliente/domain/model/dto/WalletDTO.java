package br.com.carteira.cliente.domain.model.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class WalletDTO {
	
	UUID id;
	
	String name;
	
	List<CustomerDTO> customers;
	
	List<UserDTO> users;
	
	CompanyDTO company;
}
