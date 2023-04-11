package br.com.carteira.cliente.request;

import java.util.List;

import lombok.Data;

@Data
public class BindUserRequest {
	
	List<Long> bindUserIds;
	List<Long> unbindUserIds;
	Long userId;
	
}
