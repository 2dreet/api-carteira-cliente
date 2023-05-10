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

import br.com.carteira.cliente.domain.model.dto.CourseDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ProductRequest;
import br.com.carteira.cliente.service.ProductService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping
	public ResponseEntity<CourseDTO[]> getAll() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.getAll(), CourseDTO[].class);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CourseDTO> getProduct(@PathVariable Long id) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.getProduct(id), CourseDTO.class);
	}

	@PostMapping
	public ResponseEntity<CourseDTO> createProduct(@RequestBody ProductRequest productRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.createProduct(productRequest), CourseDTO.class,
				HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CourseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.updateProduct(productRequest, id), CourseDTO.class,
				HttpStatus.ACCEPTED);
	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<String> updateProductStatus(@PathVariable Long id, @PathVariable Boolean status)
			throws RequestBodyInvalidException {

		productService.updateProductStatus(id, status);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}

}
