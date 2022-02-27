package com.example.test.services;

import com.example.test.entities.Book;
import com.example.test.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collection;

@Service
public class BookService {
	
	private BookRepository repository;
	
	@Autowired
	public void setRepository(BookRepository repository) {
		this.repository = repository;
	}
	
	public Response save(Book book) {
		Response.Status status = Response.Status.NOT_MODIFIED;
		
		if (book == null) {
			throw new NullPointerException("Book to save is null!");
		}
		
		Book result = repository.save(book);
		
		if (book.getId() == null) {
			if (result.getId() != null) {
				status = Response.Status.OK;
			}
		} else {
			if (result.equals(book)) {
				status = Response.Status.OK;
			}
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response getAll() {
		Response.Status status = Response.Status.NOT_FOUND;
		
		Collection<Book> result = repository.findAll();
		if (!result.isEmpty()) {
			status = Response.Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response delete(Long id) {
		Response.Status status = Response.Status.NOT_FOUND;
		
		if (id == null) {
			throw new NullPointerException("Id must not be null!");
		}
		if (repository.existsById(id)) {
			repository.deleteById(id);
			status = Response.Status.NO_CONTENT;
		}
		
		return Response
			.status(status)
			.build();
	}
	
	public Response get(Long id) {
		Response.Status status = Response.Status.NOT_FOUND;
		
		Book result = repository.findById(id).orElse(new Book());
		if (result.getId() != null) {
			status = Response.Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response saveAll(Collection<Book> books) {
		Response.Status status = Response.Status.NOT_MODIFIED;
		
		Collection<Book> result = repository.saveAll(books);
		if (!result.isEmpty()) {
			status = Response.Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
}
