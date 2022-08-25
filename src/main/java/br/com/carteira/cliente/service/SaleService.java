package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Customer;
import br.com.carteira.cliente.domain.model.Product;
import br.com.carteira.cliente.domain.model.Sale;
import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.repository.CustomerRepository;
import br.com.carteira.cliente.domain.repository.ProductRepository;
import br.com.carteira.cliente.domain.repository.SaleRepository;
import br.com.carteira.cliente.domain.repository.UserRepository;
import br.com.carteira.cliente.enums.SalePaymentStatusTypeEnum;
import br.com.carteira.cliente.enums.SaleStatusTypeEnum;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.SaleRequest;

@Service
public class SaleService {

	@Autowired
	SaleRepository saleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CustomerRepository customerRepository;

	public List<Sale> getSaleByProductId(Long productId) {
		if (productId == null || productId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar as vendas requisição");
		}

		List<Sale> sales = new ArrayList<>();

		saleRepository.findByProductId(productId).forEach(sale -> sales.add(sale));

		return sales;
	}

	public List<Sale> getSalesByCustomerId(Long customerId) {
		if (customerId == null || customerId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar as vendas requisição");
		}

		List<Sale> sales = new ArrayList<>();

		saleRepository.findByCustomerId(customerId).forEach(sale -> sales.add(sale));

		return sales;
	}

	public List<Sale> getSalesByUserId(Long userId) {
		if (userId == null || userId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar as vendas requisição");
		}

		List<Sale> sales = new ArrayList<>();

		saleRepository.findByUserId(userId).forEach(sale -> sales.add(sale));

		return sales;
	}

	public List<Sale> getAllSales() {
		List<Sale> sales = new ArrayList<>();

		saleRepository.findAll().forEach(sale -> sales.add(sale));

		return sales;
	}

	public Sale getSaleById(Long saleId) {
		if (saleId == null || saleId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar a venda requisição");
		}

		Sale sale = saleRepository.findById(saleId).orElse(null);
		if (sale == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Venda não encontrada");
		}

		Hibernate.initialize(sale.getCustomer());
		Hibernate.initialize(sale.getDependents());
		Hibernate.initialize(sale.getProduct());
		Hibernate.initialize(sale.getUser());

		return sale;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Sale createSale(SaleRequest saleRequest) {
		if (saleRequest == null || saleRequest.getCustomerId() == null || saleRequest.getCustomerId() <= 0
				|| saleRequest.getProductId() == null || saleRequest.getProductId() <= 0
				|| saleRequest.getUserId() == null || saleRequest.getUserId() <= 0) {

			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para criar a venda na requisição");
		}

		User user = userRepository.findById(saleRequest.getUserId()).orElse(null);
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuáro não encontrado");
		}

		Product product = productRepository.findById(saleRequest.getProductId()).orElse(null);
		if (product == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Produto não encontrado");
		}

		Customer customer = customerRepository.findById(saleRequest.getCustomerId()).orElse(null);
		if (customer == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Cliente não encontrado");
		}

		List<Customer> dependents = new ArrayList<>();
		if (saleRequest.getCustomerDependentIds() != null && saleRequest.getCustomerDependentIds().size() > 0) {
			dependents = customerRepository.findByIdIn(saleRequest.getCustomerDependentIds());
			if (dependents == null) {
				dependents = new ArrayList<>();
			}
		}

		Sale sale = new Sale();
		sale.setCustomer(customer);
		sale.setProduct(product);
		sale.setUser(user);
		sale.setValue(product.getValue());
		sale.setQuantity(saleRequest.getQuantity());
		sale.setDueDate(saleRequest.getDueDate());
		sale.setPaymentStatus(SalePaymentStatusTypeEnum.PENDING.toString());
		sale.setStatus(SaleStatusTypeEnum.TO_DO.toString());
		sale.setDependents(dependents);

		saleRepository.save(sale);

		return sale;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Sale updateSale(SaleRequest saleRequest, Long id) {
		if (saleRequest == null || id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para alterar a venda na requisição");
		}

		Sale sale = saleRepository.findById(id).orElse(null);
		if (sale == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Venda não encontrada");
		}

		List<Customer> dependents = new ArrayList<>();
		if (saleRequest.getCustomerDependentIds() != null && saleRequest.getCustomerDependentIds().size() > 0) {
			dependents = customerRepository.findByIdIn(saleRequest.getCustomerDependentIds());
			if (dependents == null) {
				dependents = new ArrayList<>();
			}
		}

		sale.setDueDate(saleRequest.getDueDate());
		sale.setQuantity(saleRequest.getQuantity());
		sale.setPaymentStatus(saleRequest.getPaymentStatus().toString());
		sale.setStatus(saleRequest.getStatus().toString());
		sale.setValue(saleRequest.getValue());
		sale.setDependents(dependents);

		saleRepository.save(sale);

		return sale;
	}

	public Sale cancelSale(Long id) {
		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para deletar a venda na requisição");
		}

		Sale sale = saleRepository.findById(id).orElse(null);
		if (sale == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Venda não encontrada");
		}
		
		sale.setPaymentStatus(SalePaymentStatusTypeEnum.CANCELED.toString());
		sale.setStatus(SaleStatusTypeEnum.CANCELED.toString());
		saleRepository.save(sale);

		return sale;
	}

}
