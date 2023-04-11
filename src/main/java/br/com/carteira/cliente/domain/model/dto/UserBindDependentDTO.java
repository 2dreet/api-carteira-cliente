package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class UserBindDependentDTO {
	
	Long id;
	
	UserSimpleDTO dependent;
}
