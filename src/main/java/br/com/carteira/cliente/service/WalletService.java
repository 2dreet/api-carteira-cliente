package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Customer;
import br.com.carteira.cliente.domain.model.User;
import br.com.carteira.cliente.domain.model.Wallet;
import br.com.carteira.cliente.domain.repository.WalletRepository;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.WalletRequest;

@Service
public class WalletService {

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	CustomerService customerService;

	@Autowired
	UserService userService;

	public Wallet getWallet(Long id) {
		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id da carteira na requisição");
		}

		Wallet wallet = walletRepository.findById(id).orElse(null);
		if (wallet == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Carteira não encontrada");
		}

		return wallet;
	}

	public List<Wallet> getWalletByUserId(Long userId) {
		if (userId == null || userId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id do usuário na requisição");
		}

		return walletRepository.findByUserId(userId);
	}

	public List<Wallet> getWalletByCustomerId(Long customerId) {
		if (customerId == null || customerId <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id do cliente na requisição");
		}
		return walletRepository.findByCustomerId(customerId);
	}
	
	public List<Wallet> getAll() {
		List<Wallet> wallets = new ArrayList<>();
		walletRepository.findAll().forEach(wallet -> wallets.add(wallet));
		return wallets;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Wallet createWallet(WalletRequest walletRequest) {
		if (walletRequest == null || StringUtils.isBlank(walletRequest.getName())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados da carteira na requisição");
		}

		Wallet wallet = new Wallet();
		wallet.setName(walletRequest.getName());
		wallet.setUser(userService.getUserInContext());

		if (walletRequest.getCustomersIds() != null && walletRequest.getCustomersIds().size() > 0) {
			List<Customer> customers = customerService.getCustomersByIds(walletRequest.getCustomersIds());
			if (customers != null && customers.size() > 0) {
				wallet.setCustomers(customers);
			}
		}

		if (walletRequest.getUsersIds() != null && walletRequest.getUsersIds().size() > 0) {
			List<User> users = userService.getUsersByIds(walletRequest.getUsersIds());
			if (users != null && users.size() > 0) {
				wallet.setUsers(users);
			}
		}

		walletRepository.save(wallet);

		return wallet;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Wallet updateWallet(WalletRequest walletRequest, Long id) {
		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id da carteira na requisição");
		} else if (walletRequest == null || StringUtils.isBlank(walletRequest.getName())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados da carteira na requisição");
		}

		Wallet wallet = walletRepository.findById(id).orElse(null);
		if (wallet == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Carteira não encontrada");
		}

		wallet.setName(walletRequest.getName());

		if (walletRequest.getCustomersIds() != null && walletRequest.getCustomersIds().size() > 0) {
			List<Customer> customers = customerService.getCustomersByIds(walletRequest.getCustomersIds());
			if (customers != null && customers.size() > 0) {
				wallet.setCustomers(customers);
			}
		}

		if (walletRequest.getUsersIds() != null && walletRequest.getUsersIds().size() > 0) {
			List<User> users = userService.getUsersByIds(walletRequest.getUsersIds());
			if (users != null && users.size() > 0) {
				wallet.setUsers(users);
			}
		}

		walletRepository.save(wallet);

		return wallet;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public void deleteWallet(Long id) {
		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id da carteira na requisição");
		}

		Wallet wallet = walletRepository.findById(id).orElse(null);
		if (wallet == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Carteira não encontrada");
		}

		walletRepository.delete(wallet);
	}
}
