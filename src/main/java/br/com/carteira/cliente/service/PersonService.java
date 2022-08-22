package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.constants.RequestExceptionConstants;
import br.com.carteira.cliente.domain.model.Person;
import br.com.carteira.cliente.domain.repository.PersonRepository;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.PersonRequest;

@Service
public class PersonService {

	@Autowired
	AddressService addressService;

	@Autowired
	ContactService contactService;

	@Autowired
	DocumentService documentService;

	@Autowired
	PersonRepository personRepository;

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Person createPerson(PersonRequest personRequest) throws RequestBodyInvalidException {
		if (personRequest == null || StringUtils.isBlank(personRequest.getName()) || personRequest.getType() == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados da pessoa na requisição");
		} else if (!validateName(personRequest.getName())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.NAME_INVALID,
					"Nome deve conter pelo menos 4 digitos");
		}

		Person person = new Person();
		person.setName(personRequest.getName());
		person.setEmail(personRequest.getEmail());
		person.setType(personRequest.getType().toString());
		person.setStatus(true);

		personRepository.save(person);

		if (personRequest.getAddresses() != null) {
			addressService.createAddresses(personRequest.getAddresses(), person);
		}

		if (personRequest.getContacts() != null) {
			contactService.createContacts(personRequest.getContacts(), person);
		}

		if (personRequest.getDocuments() != null) {
			documentService.createDocument(personRequest.getDocuments(), person);
		}

		return person;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public Person updatePerson(PersonRequest personRequest, Long id) throws RequestBodyInvalidException {
		if (id == null || id <= 0) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o id da pessoa na requisição");
		} else if (personRequest == null || StringUtils.isBlank(personRequest.getName())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado os dados da pessoa na requisição");
		} else if (!validateName(personRequest.getName())) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.NAME_INVALID,
					"Nome deve conter pelo menos 4 digitos");
		}

		Person person = personRepository.findById(id).orElse(null);
		if (person == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Pessoa não encontrada");
		}

		person.setName(personRequest.getName());
		person.setEmail(personRequest.getEmail());
		person.setBirthDate(personRequest.getBirthDate());

		personRepository.save(person);

		return person;
	}

	public void updatePersonStatus(Long id, Boolean status) throws RequestBodyInvalidException {
		if (id == null || id <= 0 || status == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID,
					"Não foi enviado o as informações para trocar o status da pessoa na requisição");
		}

		Person person = personRepository.findById(id).orElse(null);
		if (person == null) {
			throw new RequestBodyInvalidException(RequestExceptionConstants.REQUEST_INVALID, "Pessoa não encontrada");
		}

		person.setStatus(status);

		personRepository.save(person);
	}

	public List<Person> getPeopleByIds(List<Long> ids) {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<>();
		}

		return personRepository.findByIdIn(ids);
	}

	private boolean validateName(String name) {
		if (StringUtils.isNotBlank(name) && name.length() > 4) {
			return true;
		}
		return false;
	}

}
