package br.com.carteira.cliente.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.cliente.domain.model.Document;
import br.com.carteira.cliente.domain.model.Person;
import br.com.carteira.cliente.domain.repository.DocumentRepository;
import br.com.carteira.cliente.request.DocumentRequest;

@Service
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;

	public List<Document> createDocument(List<DocumentRequest> documentsRequestList, Person person) {
		List<Document> documents = new ArrayList<>();

		for (DocumentRequest documentRequest : documentsRequestList) {
			documents.add(createDocument(documentRequest, person));
		}
		
		return documents;
	}

	private Document createDocument(DocumentRequest documentRequest, Person person) {

		Document document = new Document();

		document.setNumber(documentRequest.getNumber());
		document.setType(documentRequest.getType().toString());
		document.setPerson(person);

		documentRepository.save(document);

		return document;
	}

}
