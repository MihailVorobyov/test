package com.example.test.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
public class AuthorTest {
	
	private Author author;
	
	@BeforeEach
	public void setUp() {
		author = new Author();
	}
	
	@Test
	void shouldSetAndGetId() {
		long id = 7L;
		author.setId(id);
		
		assertEquals(id, author.getId());
	}
	
	@Test
	void shouldSetAndGetFirstName() {
		String firstName = "Фёдор";
		author.setFirstName(firstName);
		
		assertEquals(firstName, author.getFirstName());
	}
	
	@Test
	void shouldSetAndGetMiddleName() {
		String middleName = "Михайлович";
		author.setMiddleName(middleName);
		
		assertEquals(middleName, author.getMiddleName());
	}
	
	@Test
	void shouldSetAndGetLastName() {
		String middleName = "Достоевский";
		author.setMiddleName(middleName);
		
		assertEquals(middleName, author.getMiddleName());
	}
	
	@Test
	void shouldThrowNullPointerException() {
		final Book book = new Book();
		assertThrowsExactly(NullPointerException.class, () -> author.addBook(book), "Wrong book!");
	}
	
	@Test
	void shouldAddBookToBooks() {
		Book book = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		author.addBook(book);
		assertEquals(1, author.getBooks().size());
	}
}
