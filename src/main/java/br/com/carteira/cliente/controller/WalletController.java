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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.WalletDTO;
import br.com.carteira.cliente.domain.model.dto.WalletSimpleDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.WalletRequest;
import br.com.carteira.cliente.service.WalletService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("wallet")
public class WalletController {

	@Autowired
	WalletService walletService;

	@GetMapping("/all")
	public ResponseEntity<WalletSimpleDTO[]> getAll() throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(walletService.getAll(), WalletSimpleDTO[].class);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<WalletDTO> getWallet(@PathVariable Long id) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(walletService.getWallet(id), WalletDTO.class);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<WalletSimpleDTO[]> getWalletByUserId(@PathVariable Long id) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(walletService.getWalletByUserId(id), WalletSimpleDTO[].class);
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<WalletSimpleDTO[]> getWalletByCustomerId(@PathVariable Long id) throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(walletService.getWalletByCustomerId(id), WalletSimpleDTO[].class);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<WalletSimpleDTO> createWallet(@RequestBody WalletRequest walletRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(walletService.createWallet(walletRequest), WalletSimpleDTO.class);
	}

	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<WalletSimpleDTO> updateWallet(@PathVariable Long id, @RequestBody WalletRequest walletRequest)
			throws RequestBodyInvalidException {
		return ReponseUtil.getResponse(walletService.updateWallet(walletRequest, id), WalletSimpleDTO.class);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<String> deleteWallet(@PathVariable Long id) throws RequestBodyInvalidException {
		walletService.deleteWallet(id);
		return ResponseEntity.ok("");
	}

}
