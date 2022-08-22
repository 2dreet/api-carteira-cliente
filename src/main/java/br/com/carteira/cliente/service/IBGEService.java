package br.com.carteira.cliente.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.dto.CityDTO;
import br.com.carteira.cliente.domain.model.dto.StateDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.exception.ServerApiRuntimeException;

@Service
public class IBGEService {

	@Value("${API_IBGE}")
	String API_IBGE;

	@Autowired
	Log log;

	public StateDTO[] getStates() {
		StateDTO[] states = null;
		try {
			RestTemplate request = new RestTemplate();
			states = request.getForObject(API_IBGE + "estados", StateDTO[].class);
		} catch (Exception e) {
			log.error("Erro ao buscar estados do IBGE " + e.getMessage());
		}

		if (states == null || states.length <= 0) {
			throw new ServerApiRuntimeException(404, "Erro ao buscar estados no serviço do IBGE");
		}

		return states;
	}
	
	public CityDTO[] getCities(String uf) {
		
		if(StringUtils.isBlank(uf)) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado a uf na requisição");
		}
		
		CityDTO[] cities = null;
		try {
			RestTemplate request = new RestTemplate();
			cities = request.getForObject(API_IBGE + "estados/" + uf + "/municipios", CityDTO[].class);
		} catch (Exception e) {
			log.error("Erro ao buscar cidades do IBGE " + e.getMessage());
		}

		if (cities == null || cities.length <= 0) {
			throw new ServerApiRuntimeException(404, "Erro ao buscar cidades no serviço do IBGE");
		}

		return cities;
	}
}
