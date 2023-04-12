package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.domain.model.Address;
import br.com.carteira.cliente.domain.model.Person;
import br.com.carteira.cliente.domain.repository.AddressRepository;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.AddressRequest;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public List<Address> createAddresses(List<AddressRequest> addressRequestList, Person person) {
		List<Address> addresses = new ArrayList<>();

		for (AddressRequest addressRequest : addressRequestList) {
			addresses.add(createAddress(addressRequest, person));
		}
		
		return addresses;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	private Address createAddress(AddressRequest addressRequest, Person person) {

		Address address = new Address();

		address.setStreet(addressRequest.getStreet());
		address.setNumber(addressRequest.getNumber());
		address.setNeighborhood(addressRequest.getNeighborhood());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setCountry(addressRequest.getCountry());
		address.setZipCode(addressRequest.getZipCode());
		address.setAdjunct(addressRequest.getAdjunct());
		address.setType(addressRequest.getType().toString());
//		address.setPerson(person);

		addressRepository.save(address);

		return address;
	}

}
