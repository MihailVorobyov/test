package com.example.test.services;

import com.example.test.entities.Author;
import com.example.test.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
	
	private AuthorRepository repository;
	
	@Autowired
	public void setRepository(AuthorRepository repository) {
		this.repository = repository;
	}
	
	public boolean save(Author author) {
		if (author == null) {
			throw new NullPointerException("Author to save is null!");
		}
		
		Author a;
		
		if (author.getId() == null) {
			a = repository.save(author);
			return a.getId() != null;
		}
		
		return false;
	}
	
	public List<Author> getAll() {
		return repository.findAll();
	}
	
	public boolean delete(Long id) {
		if (id == null) {
			throw new NullPointerException("Id must not be null!");
		}
		if (!repository.existsById(id)) {
			return false;
		} else {
			repository.deleteById(id);
			return repository.existsById(id);
		}
	}
	
	public Author get(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Author> saveAll(Iterable<Author> authors) {
		return repository.saveAll(authors);
	}
}
