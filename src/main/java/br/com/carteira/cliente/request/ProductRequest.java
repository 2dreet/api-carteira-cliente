package br.com.carteira.cliente.request;

import lombok.Data;

@Data
public class ProductRequest {

	String name;

	String description;

	Double value;

	String link;

	Long productTypeId;

}
