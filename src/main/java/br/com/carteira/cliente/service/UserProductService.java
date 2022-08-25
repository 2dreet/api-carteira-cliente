package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Product;
import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.model.UserProduct;
import br.com.carteira.cliente.domain.repository.ProductRepository;
import br.com.carteira.cliente.domain.repository.UserProductRepository;
import br.com.carteira.cliente.domain.repository.UserRepository;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.UserProductRequest;

@Service
public class UserProductService {

	@Autowired
	UserProductRepository userProductRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	public List<UserProduct> getUserProductsByProductId(Long productId) {
		if (productId == null || productId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar os produtos requisição");
		}

		List<UserProduct> userProducts = new ArrayList<>();

		userProductRepository.findByProductId(productId).forEach(userProduct -> userProducts.add(userProduct));

		return userProducts;
	}

	public List<UserProduct> getUserProducts(Long userId) {
		if (userId == null || userId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para localizar os produtos requisição");
		}

		List<UserProduct> userProducts = new ArrayList<>();

		userProductRepository.findByUserId(userId).forEach(userProduct -> userProducts.add(userProduct));

		return userProducts;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public List<UserProduct> createUserProduct(UserProductRequest userProductRequest) {
		if (userProductRequest == null || userProductRequest.getUserIds() == null
				|| userProductRequest.getUserIds().size() == 0 || userProductRequest.getProductId() == null
				|| userProductRequest.getProductId() <= 0) {

			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para vincular o produto com o usuário na requisição");
		}

		List<UserProduct> userProducts = new ArrayList<>();
		for (Long userId : userProductRequest.getUserIds()) {
			userProducts.add(attachUserProduct(userId, userProductRequest.getProductId()));
		}
		return userProducts;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	private UserProduct attachUserProduct(Long userId, Long productId) {
		if (userId == null || userId <= 0 || productId == null || productId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados para vincular o produto com o usuário na requisição");
		}

		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Usuáro não encontrado");
		}

		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Produto não encontrado");
		}

		UserProduct userProduct = userProductRepository.findByUserIdAndProductId(userId, productId);
		if (userProduct == null) {
			userProduct = new UserProduct();
			userProduct.setProduct(product);
			userProduct.setUser(user);

			userProductRepository.save(userProduct);
		}

		return userProduct;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void deleteUserProduct(UserProductRequest userProductRequest) {
		if (userProductRequest == null || userProductRequest.getUserIds() == null
				|| userProductRequest.getUserIds().size() == 0 || userProductRequest.getProductId() == null
				|| userProductRequest.getProductId() <= 0) {

			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados do para desvincular o produto com o usuário na requisição");
		}

		for (Long userId : userProductRequest.getUserIds()) {
			unattachUserProduct(userId, userProductRequest.getProductId());
		}
	}

	private void unattachUserProduct(Long userId, Long productId) {
		UserProduct userProduct = userProductRepository.findByUserIdAndProductId(userId, productId);
		if (userProduct != null) {
			userProductRepository.delete(userProduct);
		}
	}

}
