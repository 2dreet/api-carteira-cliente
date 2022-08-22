package br.com.carteira.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.cliente.domain.model.dto.CityDTO;
import br.com.carteira.cliente.domain.model.dto.StateDTO;
import br.com.carteira.cliente.service.IBGEService;
import br.com.carteira.cliente.util.ReponseUtil;

@RestController
@RequestMapping("ibge")
public class IBGEController {

	@Autowired
	IBGEService ibgeService;

	@GetMapping("states")
	public ResponseEntity<StateDTO []> getStates() {
		return ReponseUtil.getResponse(ibgeService.getStates(), StateDTO[].class);
	}

	@GetMapping("cities/{uf}")
	public ResponseEntity<CityDTO []> getCities(@PathVariable String uf) {
		return ReponseUtil.getResponse(ibgeService.getCities(uf), CityDTO[].class);
	}

}
