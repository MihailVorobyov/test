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
		
		Author a = repository.save(author);
		
		if (author.getId() == null) {
			return a.getId() != null;
		} else {
			return author.equals(a);
		}
	}
	
	public List<Author> getAll() {
		return repository.findAll();
	}
	
	public boolean delete(Author author) {
		Long id = author.getId();
		if (!repository.existsById(id)) {
			return false;
		} else {
			repository.deleteById(id);
			return true;
		}
	}
	
	public Author get(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Author> saveAll(Iterable<Author> authors) {
		return repository.saveAll(authors);
	}
}
