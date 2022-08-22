package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@GetMapping("/all")
	public ResponseEntity<CustomerDTO[]> getAll() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(customerService.getAll(), CustomerDTO[].class);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(customerService.getCustomer(id), CustomerDTO.class);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerRequest customerRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(customerService.createCustomer(customerRequest), CustomerDTO.class);
	}

	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,
			@RequestBody CustomerRequest customerRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(customerService.updateCustomer(customerRequest, id), CustomerDTO.class);
	}

	@PutMapping("/{id}/status")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<CustomerDTO> updateCustomerStatus(@PathVariable Long id,
			@RequestBody CustomerRequest customerRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(customerService.updateCustomerStatus(customerRequest, id), CustomerDTO.class);
	}

	@PutMapping("/{id}/person/status/{status}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<String> updateCustomerPersonStatus(@PathVariable Long id, @PathVariable Boolean status)
			throws RequestBodyInvalidException {

		customerService.updateCustomerPersonStatus(id, status);
		return ResponseEntity.ok("");
	}

}
