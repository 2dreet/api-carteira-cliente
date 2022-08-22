package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.CepDTO;
import br.com.carteira.cliente.service.CepService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("cep")
public class CepController {

	@Autowired
	CepService cepService;

	@GetMapping("{cep}")
	public ResponseEntity<CepDTO> getCities(@PathVariable String cep) {
		return ReponseUtil.getResponse(cepService.getCep(cep), CepDTO.class);
	}

}
