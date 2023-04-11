package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class UserBindManagerDTO {
	
	Long id;
	
	UserSimpleDTO manager;
}
