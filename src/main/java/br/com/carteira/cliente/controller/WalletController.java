package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.WalletDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.WalletRequest;
import br.com.carteira.cliente.service.WalletService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("wallet")
public class WalletController {

	@Autowired
	WalletService walletService;

	@PostMapping
	public ResponseEntity<WalletDTO> createWallet(@RequestBody WalletRequest walletRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(walletService.createWallet(walletRequest), WalletDTO.class);
	}

}
