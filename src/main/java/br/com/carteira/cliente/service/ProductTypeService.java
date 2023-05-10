package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Course;
import br.com.carteira.cliente.domain.model.ProductType;
import br.com.carteira.cliente.domain.repository.CouseRepository;
import br.com.carteira.cliente.domain.repository.ProductTypeRepository;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ProductTypeRequest;

@Service
public class ProductTypeService {

	@Autowired
	ProductTypeRepository productTypeRepository;

	@Autowired
	CouseRepository productRepository;

	public List<ProductType> getAll() {
		List<ProductType> productTypes = new ArrayList<>();
		productTypeRepository.findAll().forEach(productType -> productTypes.add(productType));
		return productTypes;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public ProductType createProductType(ProductTypeRequest productTypeRequest) {
		if (productTypeRequest == null || StringUtils.isBlank(productTypeRequest.getName())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do tipo de produto na requisição");
		} else if (!validateProductTypeName(productTypeRequest.getName(), null)) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Já existe um tipo de produto com esse nome");
		}

		ProductType productType = new ProductType();
		productType.setName(productTypeRequest.getName());

		productTypeRepository.save(productType);

		return productType;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public ProductType updateProductType(ProductTypeRequest productTypeRequest, Long id) {
		if (productTypeRequest == null || StringUtils.isBlank(productTypeRequest.getName()) || id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do tipo de produto na requisição");
		} else if (!validateProductTypeName(productTypeRequest.getName(), id)) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Já existe um tipo de produto com esse nome");
		}

		ProductType productType = productTypeRepository.findById(id).orElse(null);
		if (productType == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Tipo de produto não encontrado");
		}

		productType.setName(productTypeRequest.getName());

		productTypeRepository.save(productType);

		return productType;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void deleteProductType(Long id) {
		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do tipo de produto na requisição");
		}
		
		ProductType productType = productTypeRepository.findById(id).orElse(null);
		if (productType == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Tipo de produto não encontrado");
		}
		
		List<Course> products = productRepository.findByProductTypeId(id);
		if(products != null && products.size() > 0 ) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não é possível deletar o tipo do produto, pois, existem produtos cadastros com esse tipo");
		}
		
		productTypeRepository.delete(productType);
	}

	private boolean validateProductTypeName(String name, Long id) {
		ProductType productType = productTypeRepository.findByName(name);
		if (productType == null || (id != null && productType.getId() == id)) {
			return true;
		}

		return false;
	}
}
