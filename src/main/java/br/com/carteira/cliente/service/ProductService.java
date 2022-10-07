package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Product;
import br.com.carteira.cliente.domain.model.ProductType;
import br.com.carteira.cliente.domain.repository.ProductRepository;
import br.com.carteira.cliente.domain.repository.ProductTypeRepository;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ProductRequest;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductTypeRepository productTypeRepository;
	
	@Autowired
	UserService userService;

	public List<Product> getAll() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(product -> products.add(product));
		return products;
	}
	
	public Product getProduct(Long id) {

		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id do produto na requisição");
		}

		Product product = productRepository.findById(id).orElse(null);
		if (product == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Produto não encontrado");
		}

		return product;
	}
	
	public List<Product> getByProductTypeId(Long id) {
		if( id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do tipo de produto na requisição");
		}
		
		return productRepository.findByProductTypeId(id);
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Product createProduct(ProductRequest productRequest) {
		if (productRequest == null || StringUtils.isBlank(productRequest.getName()) 
				|| productRequest.getValue() == 0 || productRequest.getValue() == null 
				|| productRequest.getProductTypeId() == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do produto na requisição");
		} else if (!validateProductName(productRequest.getName(), null)) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Já existe um produto com esse nome");
		}

		ProductType productType = productTypeRepository.findById(productRequest.getProductTypeId()).orElse(null);
		if(productType == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Tipo de produto não encontrado");
		}
		
		Product product = new Product();
		product.setName(productRequest.getName());
		product.setStatus(true);
		product.setValue(productRequest.getValue());
		product.setDescription(productRequest.getDescription());
		product.setLink(productRequest.getLink());
		product.setProductType(productType);
		
		product.setUser(userService.getUserInContext());

		productRepository.save(product);

		return product;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Product updateProduct(ProductRequest productRequest, Long id) {
		if (productRequest == null || StringUtils.isBlank(productRequest.getName()) 
				|| productRequest.getValue() == 0 || productRequest.getValue() == null 
				|| productRequest.getProductTypeId() == null || id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do produto na requisição");
		} else if (!validateProductName(productRequest.getName(), id)) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Já existe um produto com esse nome");
		}

		ProductType productType = productTypeRepository.findById(productRequest.getProductTypeId()).orElse(null);
		if(productType == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Tipo de produto não encontrado");
		}
		
		Product product = productRepository.findById(id).orElse(null);
		if (product == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Produto não encontrado");
		}

		product.setName(productRequest.getName());
		product.setValue(productRequest.getValue());
		product.setDescription(productRequest.getDescription());
		product.setLink(productRequest.getLink());
		product.setProductType(productType);

		productRepository.save(product);

		return product;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void updateProductStatus(Long id, Boolean status) {
		if (id == null || id <= 0 || status == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do produto na requisição");
		}
		
		Product product = productRepository.findById(id).orElse(null);
		if (product == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Produto não encontrado");
		}
		
		product.setStatus(status);
		
		productRepository.save(product);
	}

	private boolean validateProductName(String name, Long id) {
		Product product = productRepository.findByName(name);
		if (product == null || (id != null && product.getId() == id)) {
			return true;
		}
		return false;
	}
}
