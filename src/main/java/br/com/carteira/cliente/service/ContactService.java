package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.domain.model.Contact;
import br.com.carteira.cliente.domain.model.Person;
import br.com.carteira.cliente.domain.repository.ContactRepository;
import br.com.carteira.cliente.exception.RequestBodyInvalidException;
import br.com.carteira.cliente.request.ContactRequest;

@Service
public class ContactService {

	@Autowired
	ContactRepository contactRepository;

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	public List<Contact> createContacts(List<ContactRequest> contactRequests, Person person) {
		List<Contact> contacts = new ArrayList<>();

		for (ContactRequest contactsRequest : contactRequests) {
			contacts.add(createContact(contactsRequest, person));
		}

		return contacts;
	}

	@Transactional(rollbackOn = RequestBodyInvalidException.class)
	private Contact createContact(ContactRequest contactRequest, Person person) {

		Contact contact = new Contact();

		contact.setNumber(contactRequest.getNumber());
		contact.setType(contactRequest.getType().toString());
		contact.setPerson(person);

		contactRepository.save(contact);

		return contact;
	}

}
