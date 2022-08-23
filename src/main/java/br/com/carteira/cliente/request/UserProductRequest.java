package br.com.carteira.cliente.request;

import java.util.List;

import lombok.Data;

@Data
public class UserProductRequest {

	Long productId;
	
	List<Long> userIds;
}
