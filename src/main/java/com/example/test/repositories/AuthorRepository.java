package com.example.test.repositories;

import com.example.test.entities.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	@Override
	List<Author> findAll();
	
	Author findFirstByLastName(String lastName);
	
	@Override
	<S extends Author> List<S> saveAll(Iterable<S> entities);
}
