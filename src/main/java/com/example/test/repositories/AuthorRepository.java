package com.example.test.repositories;

import com.example.test.entities.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	@Override
	List<Author> findAll();
	
	@Override
	<S extends Author> S save(S entity);
	
	@Override
	void deleteById(Long aLong);
	
	@Override
	boolean existsById(Long aLong);
	
	@Override
	Optional<Author> findById(Long aLong);
	
	@Override
	<S extends Author> List<S> saveAll(Iterable<S> entities);
}
