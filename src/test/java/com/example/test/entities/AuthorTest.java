package com.example.test.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
	
	@Test
	void shouldReturnTrueWhenEquals() {
		Author author1 = new Author(null, "Фёдор", "Михайлович", "Достоевский");
		Author author2 = new Author(1L, "Фёдор", "Михайлович", "Достоевский");
		Author author3 = new Author(1L, "Олег", "Михайлович", "Достоевский");
		Author author4 = new Author(2L, "Фёдор", "Михайлович", "Достоевский");
		Author author5 = author1;
		
		assertEquals(author1, author5);
		assertNotEquals(author2, new Object());
		assertEquals(author1, author2);
		assertEquals(author2, author1);
		assertNotEquals(author1, author3);
		assertEquals(author2, author4);
		assertNotEquals(author4, null);
		assertEquals(author3, author3);

		
		author1.addBook(new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now()));
		assertNotEquals(author1, author2);
	}
	
	@Test
	void shouldReturnSameHashCodeIfObjectsEquals() {
		Author a1 = new Author(null, "Фёдор", "Михайлович", "Достоевский");
		Author a2 = new Author(1L, "Фёдор", "Михайлович", "Достоевский");
		
		assertEquals(a1.hashCode(), a2.hashCode());
		for (int i = 0; i < 15; i++) {
			assertEquals(a1.hashCode(), new Author(1L, "Фёдор", "Михайлович", "Достоевский").hashCode());
		}
		
		assertNotEquals(a1.hashCode(), new Author(1L, "Федор", "Михайлович", "Достоевский").hashCode());
	}
}
