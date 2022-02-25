package com.example.test.services;

import com.example.test.entities.Book;
import com.example.test.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
	
	private BookRepository repository;
	
	@Autowired
	public void setRepository(BookRepository repository) {
		this.repository = repository;
	}
	
	public boolean save(Book book) {
		if (book == null) {
			throw new NullPointerException();
		}
		Book b;
		if (book.getId() == null) {
			b = repository.save(book);
			return b.getId() != null;
		} else {
			b = repository.save(book);
			return book.equals(b);
		}
	}
	
	public List<Book> getAll() {
		return repository.findAll();
	}
	
	public boolean delete(Book book) {
		if (book == null) {
			throw new NullPointerException("Can not delete book because it is null!");
		}
		if (book.getId() == null) {
			return false;
		}
		
		if (repository.existsById(book.getId())) {
			repository.deleteById(book.getId());
			return true;
		} else {
			return false;
		}
	}
	
	public Book get(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Book> saveAll(Iterable<Book> books) {
		return repository.saveAll(books);
	}
}
