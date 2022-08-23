package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Customer;
import br.com.carteira.cliente.domain.model.CustomerProduct;
import br.com.carteira.cliente.domain.model.Product;
import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.repository.CustomerProductRepository;
import br.com.carteira.cliente.domain.repository.CustomerRepository;
import br.com.carteira.cliente.domain.repository.ProductRepository;
import br.com.carteira.cliente.domain.repository.UserRepository;
import br.com.carteira.cliente.enums.CustomerProductPaymentStatusTypeEnum;
import br.com.carteira.cliente.enums.CustomerProductStatusTypeEnum;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.CustomerProductRequest;

@Service
public class CustomerProductService {

	@Autowired
	CustomerProductRepository customerProductRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CustomerRepository customerRepository;

	public List<CustomerProduct> getCustomerProductsByProductId(Long productId) {
		if (productId == null || productId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar os produtos requisição");
		}

		List<CustomerProduct> customerProducts = new ArrayList<>();

		customerProductRepository.findByProductId(productId)
				.forEach(customerProduct -> customerProducts.add(customerProduct));

		return customerProducts;
	}
	
	public List<CustomerProduct> getCustomerProducts(Long customerId) {
		if (customerId == null || customerId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar os produtos requisição");
		}

		List<CustomerProduct> customerProducts = new ArrayList<>();

		customerProductRepository.findByCustomerId(customerId)
				.forEach(customerProduct -> customerProducts.add(customerProduct));

		return customerProducts;
	}

	public CustomerProduct createCustomerProduct(CustomerProductRequest customerProductRequest) {
		if (customerProductRequest == null || customerProductRequest.getCustomerId() == null
				|| customerProductRequest.getCustomerId() <= 0 || customerProductRequest.getProductId() == null
				|| customerProductRequest.getProductId() <= 0 || customerProductRequest.getUserId() == null
				|| customerProductRequest.getUserId() <= 0) {

			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados vincular o  produto com o cliente na requisição");
		}

		User user = userRepository.findById(customerProductRequest.getUserId()).orElse(null);
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuáro não encontrado");
		}

		Product product = productRepository.findById(customerProductRequest.getProductId()).orElse(null);
		if (product == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Produto não encontrado");
		}

		Customer customer = customerRepository.findById(customerProductRequest.getCustomerId()).orElse(null);
		if (customer == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Cliente não encontrado");
		}

		CustomerProduct customerProduct = new CustomerProduct();
		customerProduct.setCustomer(customer);
		customerProduct.setProduct(product);
		customerProduct.setUser(user);
		customerProduct.setValue(product.getValue());
		customerProduct.setDueDate(customerProductRequest.getDueDate());
		customerProduct.setPaymentStatus(CustomerProductPaymentStatusTypeEnum.PENDING.toString());
		customerProduct.setStatus(CustomerProductStatusTypeEnum.TO_DO.toString());

		customerProductRepository.save(customerProduct);

		return customerProduct;
	}

	public CustomerProduct updateCustomerProduct(CustomerProductRequest customerProductRequest, Long id) {
		if (customerProductRequest == null || id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados vincular o  produto com o cliente na requisição");
		}

		CustomerProduct customerProduct = customerProductRepository.findById(id).orElse(null);
		if (customerProduct == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Vinculo com produto não encontrado");
		}

		customerProduct.setDueDate(customerProductRequest.getDueDate());
		customerProduct.setPaymentStatus(customerProductRequest.getPaymentStatus().toString());
		customerProduct.setStatus(customerProductRequest.getStatus().toString());
		customerProduct.setValue(customerProductRequest.getValue());

		customerProductRepository.save(customerProduct);

		return customerProduct;
	}

	public void deleteCustomerProduct(Long id) {
		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados vincular o  produto com o cliente na requisição");
		}

		CustomerProduct customerProduct = customerProductRepository.findById(id).orElse(null);
		if (customerProduct != null) {
			customerProductRepository.delete(customerProduct);
		}

	}

}
