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
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.SaleDTO;
import br.com.carteira.cliente.domain.model.dto.SaleSimpleDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.SaleRequest;
import br.com.carteira.cliente.service.SaleService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("sale")
public class SaleController {

	@Autowired
	SaleService saleService;
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<SaleSimpleDTO[]> getSaleByCustomer(@PathVariable Long customerId)
			throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(saleService.getSalesByCustomerId(customerId),
				SaleSimpleDTO[].class);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<SaleSimpleDTO[]> getSalesByProduct(@PathVariable Long productId)
			throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(saleService.getSaleByProductId(productId),
				SaleSimpleDTO[].class);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<SaleSimpleDTO[]> getSalesByUser(@PathVariable Long userId)
			throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(saleService.getSalesByUserId(userId),
				SaleSimpleDTO[].class);
	}
	
	@GetMapping
	public ResponseEntity<SaleSimpleDTO[]> getAllSales()
			throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(saleService.getAllSales(),
				SaleSimpleDTO[].class);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SaleDTO> getSalesById(@PathVariable Long id)
			throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(saleService.getSaleById(id),
				SaleDTO.class);
	}

	@PostMapping
	public ResponseEntity<SaleSimpleDTO> createSale(
			@RequestBody SaleRequest customerProductRequest) throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(saleService.createSale(customerProductRequest),
				SaleSimpleDTO.class, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<SaleSimpleDTO> updateSale(@PathVariable Long id,
			@RequestBody SaleRequest customerProductRequest) throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(saleService.updateSale(customerProductRequest, id),
				SaleSimpleDTO.class, HttpStatus.ACCEPTED);
	}

	@PutMapping("/cancel/{id}")
	public ResponseEntity<SaleSimpleDTO> cancelSale(@PathVariable Long id) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(saleService.cancelSale(id),
				SaleSimpleDTO.class, HttpStatus.ACCEPTED);
	}
}
