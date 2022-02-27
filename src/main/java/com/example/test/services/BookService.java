package com.example.test.services;

import com.example.test.entities.Author;
import com.example.test.entities.Book;
import com.example.test.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;

@Service
public class BookService {
	
	private BookRepository repository;
	
	@Autowired
	public void setRepository(BookRepository repository) {
		this.repository = repository;
	}
	
	public Response save(Book book) {
		Status status = Status.NOT_MODIFIED;
		
		if (book == null) {
			throw new NullPointerException("Book to save is null!");
		}
		
		Book result = repository.save(book);
		
		if (book.getId() == null) {
			if (result.getId() != null) {
				status = Status.OK;
			}
		} else {
			if (result.equals(book)) {
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
		
		Collection<Book> result = repository.findAll();
		if (!result.isEmpty()) {
			status = Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response getAllByAuthorId(Long id) {
		Status status = Status.NOT_FOUND;
		
		Collection<Book> result = repository.findAllByAuthorId(id);
		if (!result.isEmpty()) {
			status = Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response getAllByTitle(String title) {
		Status status = Status.NOT_FOUND;
		
		Collection<Book> result = repository.findAllByTitleLike("%" + title + "%");
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
		
		Book result = repository.findById(id).orElse(new Book());
		if (result.getId() != null) {
			status = Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
	
	public Response saveAll(Collection<Book> books) {
		Status status = Status.NOT_MODIFIED;
		
		Collection<Book> result = repository.saveAll(books);
		if (!result.isEmpty()) {
			status = Status.OK;
		}
		
		return Response
			.status(status)
			.entity(result)
			.build();
	}
}
