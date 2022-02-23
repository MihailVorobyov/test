package com.example.test.repositories;

import com.example.test.entities.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorRepositoryTest {

	@Autowired
	private AuthorRepository repository;
	
	private Author author;
	
	@BeforeEach
	void setUp() {
		author = new Author(null, "Лев", "Николаевич", "Толстой");
	}
	
	@Test
	public void shouldReturnAuthorWithIdAfterAdd() {
		int authorsCount = repository.findAll().size();
		assertEquals(0,authorsCount);
		
		// Сохранение нового объекта
		Author authorAfterSave = repository.save(author);
		assertNotNull(authorAfterSave.getId());
		
		Author authorNew = new Author(null, "Фёдор", "Михайлович", "Достоевский");
		Author authorAfterSave2 = repository.save(authorNew);
		assertNotNull(authorAfterSave2.getId());
		assertNotEquals(authorAfterSave.getId(), authorAfterSave2.getId());
		
		// Обновление объекта
		authorAfterSave2.setFirstName("Fedor");
		authorAfterSave2.setMiddleName("Mihailovich");
		authorAfterSave2.setLastName("Dostoevsky");
		authorAfterSave2 = repository.save(authorAfterSave2);
		assertEquals(authorAfterSave2.getFirstName(), "Fedor");
	}
	
	@Test
	void shouldDeleteAuthorById() {
		Author aPushkin = new Author(null, "Александр", "Сергеевич", "Пушкин");
		repository.save(aPushkin);
		
		repository.deleteById(1L);
		assertFalse(repository.existsById(1L));
	}
}

