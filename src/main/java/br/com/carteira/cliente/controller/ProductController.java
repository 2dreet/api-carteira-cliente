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

import br.com.carteira.cliente.domain.model.dto.CustomerProductDTO;
import br.com.carteira.cliente.domain.model.dto.ProductDTO;
import br.com.carteira.cliente.domain.model.dto.UserProductDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ProductRequest;
import br.com.carteira.cliente.service.CustomerProductService;
import br.com.carteira.cliente.service.ProductService;
import br.com.carteira.cliente.service.UserProductService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CustomerProductService customerProductService;
	
	@Autowired
	UserProductService userProductService;

	@GetMapping
	public ResponseEntity<ProductDTO[]> getAll() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.getAll(), ProductDTO[].class);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.getProduct(id), ProductDTO.class);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequest productRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.createProduct(productRequest), ProductDTO.class,
				HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(productService.updateProduct(productRequest, id), ProductDTO.class,
				HttpStatus.ACCEPTED);
	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<String> updateProductStatus(@PathVariable Long id, @PathVariable Boolean status)
			throws RequestBodyInvalidException {

		productService.updateProductStatus(id, status);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
	}
	
	@GetMapping("/{productId}/customer")
	public ResponseEntity<CustomerProductDTO[]> getCustomerProducts(@PathVariable Long productId)
			throws RequestBodyInvalidException {

		return ReponseUtil.getResponse(customerProductService.getCustomerProductsByProductId(productId),
				CustomerProductDTO[].class);
	}
	
	@GetMapping("/{productId}/user")
	public ResponseEntity<UserProductDTO[]> getUserProducts(@PathVariable Long productId)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(userProductService.getUserProductsByProductId(productId), UserProductDTO[].class);
	}

}
