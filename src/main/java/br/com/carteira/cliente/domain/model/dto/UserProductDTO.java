package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class UserProductDTO {

	Long id;
	
	ProductDTO product;
	
	UserDTO user;

}
