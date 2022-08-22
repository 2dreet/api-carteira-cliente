package br.com.carteira.cliente.request;

import br.com.carteira.cliente.enums.CustomerStatusEnum;
import lombok.Data;

@Data
public class CustomerRequest {

	PersonRequest person;
	
	CustomerStatusEnum status;
}
