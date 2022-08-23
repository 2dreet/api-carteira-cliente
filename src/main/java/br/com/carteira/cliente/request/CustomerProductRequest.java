package br.com.carteira.cliente.request;

import br.com.carteira.cliente.enums.CustomerProductPaymentStatusTypeEnum;
import br.com.carteira.cliente.enums.CustomerProductStatusTypeEnum;
import lombok.Data;

@Data
public class CustomerProductRequest {

	String dueDate;

	CustomerProductStatusTypeEnum status;

	CustomerProductPaymentStatusTypeEnum paymentStatus;

	Double value;

	Long productId;

	Long customerId;

	Long userId;
}
