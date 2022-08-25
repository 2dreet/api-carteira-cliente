package br.com.carteira.cliente.request;

import java.util.List;

import br.com.carteira.cliente.enums.SalePaymentStatusTypeEnum;
import br.com.carteira.cliente.enums.SaleStatusTypeEnum;
import lombok.Data;

@Data
public class SaleRequest {

	String dueDate;

	SaleStatusTypeEnum status;

	SalePaymentStatusTypeEnum paymentStatus;

	Double value;
	
	Long quantity;

	Long productId;

	Long customerId;
	
	Long userId;
	
	List<Long> customerDependentIds;
}
