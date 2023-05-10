package br.com.carteira.cliente.domain.model.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CompanyDTO {

	UUID id;
	
	String name;
	
	String cnpj;
	
	String representativeCpf;
}
