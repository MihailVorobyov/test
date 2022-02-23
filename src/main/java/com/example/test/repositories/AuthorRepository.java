package com.example.test.repositories;

import com.example.test.entities.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	@Override
	Set<Author> findAll();
	
	Author findFirstByLastName(String lastName);
	
	@Override
	<S extends Author> Set<S> saveAll(Iterable<S> entities);
}
