package br.com.carteira.cliente.domain.model.dto;

import lombok.Data;

@Data
public class CepDTO {
	String cep, logradouro, bairro, localidade, uf;
	boolean erro;
}
