package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Customer;
import br.com.carteira.cliente.domain.model.Person;
import br.com.carteira.cliente.domain.repository.CustomerRepository;
import br.com.carteira.cliente.enums.CustomerStatusEnum;
import br.com.carteira.cliente.enums.PersonTypeEnum;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.CustomerRequest;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PersonService personService;

	public List<Customer> getAll() {
		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customer -> customers.add(customer));
		return customers;
	}

	public Customer getCustomer(Long id) {

		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id do cliente na requisição");
		}

		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Cliente não encontrado");
		}
		
		Hibernate.initialize(customer.getResponsible());
		Hibernate.initialize(customer.getDependents());

		return customer;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Customer createCustomer(CustomerRequest customerRequest) {
		if (customerRequest == null || customerRequest.getPerson() == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do cliente na requisição");
		}

		Person person = personService.createPerson(customerRequest.getPerson(), PersonTypeEnum.CUSTOMER);

		Customer customer = new Customer();
		customer.setPerson(person);
		customer.setStatus(CustomerStatusEnum.NORMAL.toString());

		customerRepository.save(customer);

		if (customerRequest.getCustomerDependentIds() != null) {
			for (Long id : customerRequest.getCustomerDependentIds()) {
				if (id != null && id > 0) {
					Customer dependente = customerRepository.findById(id).orElse(null);
					if(dependente != null) {
						dependente.setResponsible(customer);
						customerRepository.save(dependente);
					}
				}
			}
		}
		
		return customer;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Customer updateCustomer(CustomerRequest customerRequest, Long id) {

		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id do cliente na requisição");
		} else if (customerRequest == null || customerRequest.getPerson() == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do cliente na requisição");
		}
		
		customerRepository.removeResponsible(id);

		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Cliente não encontrado");
		}

		if (customerRequest.getStatus() != null) {
			customer.setStatus(customerRequest.getStatus().toString());
		}

		personService.updatePerson(customerRequest.getPerson(), customer.getPerson().getId());

		customerRepository.save(customer);
		
		if (customerRequest.getCustomerDependentIds() != null) {
			for (Long idDependent : customerRequest.getCustomerDependentIds()) {
				if (idDependent != null && idDependent > 0) {
					Customer dependente = customerRepository.findById(idDependent).orElse(null);
					if(dependente != null) {
						dependente.setResponsible(customer);
						customerRepository.save(dependente);
					}
				}
			}
		}

		return customer;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Customer updateCustomerStatus(CustomerRequest customerRequest, Long id) {

		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id do cliente na requisição");
		} else if (customerRequest == null || customerRequest.getStatus() == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do cliente na requisição");
		}

		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Cliente não encontrado");
		}

		customer.setStatus(customerRequest.getStatus().toString());

		customerRepository.save(customer);

		return customer;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void updateCustomerPersonStatus(Long id, Boolean status) {

		if (id == null || id <= 0 || status == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id ou o status do cliente na requisição");
		}

		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Cliente não encontrado");
		}

		personService.updatePersonStatus(customer.getPerson().getId(), status);

		customerRepository.save(customer);
	}

	public List<Customer> getCustomersByIds(List<Long> ids) {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<>();
		}

		return customerRepository.findByIdIn(ids);
	}

}
