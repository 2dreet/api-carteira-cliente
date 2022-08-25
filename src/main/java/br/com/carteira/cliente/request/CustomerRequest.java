package br.com.carteira.cliente.request;

import java.util.List;

import br.com.carteira.cliente.enums.CustomerStatusEnum;
import lombok.Data;

@Data
public class CustomerRequest {

	PersonRequest person;
	
	CustomerStatusEnum status;
	
	List<Long> customerDependentIds;
}
