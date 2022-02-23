package com.example.test.repositories;

import com.example.test.entities.Author;
import com.example.test.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	
	List<Book> findAllByAuthor(Author author);
	
	List<Book> findOneByTitle(String title);
	
	@Override
	List<Book> findAll();
	
	@Override
	<S extends Book> Set<S> saveAll(Iterable<S> entities);
}
