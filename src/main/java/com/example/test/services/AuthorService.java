package com.example.test.services;

import com.example.test.entities.Author;
import com.example.test.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;

@Service
public class AuthorService {
	
	private AuthorRepository repository;
	
	@Autowired
	public void setRepository(AuthorRepository repository) {
		this.repository = repository;
	}
	
	public Response save(Author author) {
		Status status = Status.NOT_MODIFIED;
		
		if (author == null) {
			throw new NullPointerException("Author to save is null!");
		}
		
		Author result = repository.save(author);
		
		if (author.getId() == null) {
			if (result.getId() != null) {
				status = Status.OK;
			}
		} else {
			if (result.equals(author)) {
				status = Status.OK;
			}
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response getAll() {
		Status status = Status.NOT_FOUND;
		
		Collection<Author> result = repository.findAll();
		if (!result.isEmpty()) {
			status = Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response delete(Long id) {
		Status status = Status.NOT_FOUND;
		
		if (id == null) {
			throw new NullPointerException("Id must not be null!");
		}
		if (repository.existsById(id)) {
			repository.deleteById(id);
			status = Status.NO_CONTENT;
		}
		
		return Response
			.status(status)
			.build();
	}
	
	public Response get(Long id) {
		Status status = Status.NOT_FOUND;
		
		Author result = repository.findById(id).orElse(new Author());
		if (result.getId() != null) {
			status = Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response saveAll(Collection<Author> authors) {
		Status status = Status.NOT_MODIFIED;
		
		Collection<Author> result = repository.saveAll(authors);
		if (!result.isEmpty()) {
			status = Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
}
