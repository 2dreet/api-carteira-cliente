package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.ProductTypeDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ProductTypeRequest;
import br.com.carteira.cliente.service.ProductTypeService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("product-type")
public class ProductTypeController {

	@Autowired
	ProductTypeService productTypeService;

	@GetMapping
	public ResponseEntity<ProductTypeDTO[]> getAll() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productTypeService.getAll(), ProductTypeDTO[].class);
	}

	@PostMapping
	public ResponseEntity<ProductTypeDTO> createProductType(@RequestBody ProductTypeRequest productTypeRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productTypeService.createProductType(productTypeRequest), ProductTypeDTO.class,
				HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductTypeDTO> updateProductType(@PathVariable Long id,
			@RequestBody ProductTypeRequest productTypeRequest) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productTypeService.updateProductType(productTypeRequest, id),
				ProductTypeDTO.class, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProductType(@PathVariable Long id) throws RequestBodyInvalidException {
		productTypeService.deleteProductType(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}

}
