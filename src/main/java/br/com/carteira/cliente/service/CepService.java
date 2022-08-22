package br.com.carteira.cliente.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.dto.CepDTO;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.exception.ServerApiRuntimeException;

@Service
public class CepService {

	@Value("${API_CEP}")
	String API_CEP;

	@Autowired
	Log log;

	public CepDTO getCep(String cep) {
		
		if(StringUtils.isBlank(cep)) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o CEP na requisição");
		}
		
		CepDTO cepDTO = null;
		try {
			RestTemplate request = new RestTemplate();
			cepDTO = request.getForObject(API_CEP + cep + "/json", CepDTO.class);
		} catch (Exception e) {
			log.error("Erro ao buscar CEP " + e.getMessage());
		}

		if (cepDTO == null || cepDTO.isErro()) {
			throw new ServerApiRuntimeException(404, "Erro ao buscar CEP");
		}

		return cepDTO;
	}
}
