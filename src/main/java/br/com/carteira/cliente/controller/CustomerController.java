package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.CustomerDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.CustomerRequest;
import br.com.carteira.cliente.service.CustomerService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerRequest customerRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(customerService.createCustomer(customerRequest), CustomerDTO.class);
	}

}
