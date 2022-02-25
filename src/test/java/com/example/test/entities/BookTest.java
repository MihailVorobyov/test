package com.example.test.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookTest {
	
	private Book book;
	
	@BeforeEach
	public void setUp() {
		book = new Book();
	}
	
	@Test
	void shouldSetAndGetId() {
		long id = 9L;
		book.setId(id);
		
		assertEquals(id, book.getId());
	}
	
	@Test
	void shouldSetAndGetISBN() {
		String ISBN = "123-456-7";
		book.setISBN(ISBN);
		
		assertEquals(ISBN, book.getISBN());
	}
	
	@Test
	void shouldSetAndGetTitle() {
		String title = "Анна Каренина";
		book.setTitle(title);
		
		assertEquals(title, book.getTitle());
	}
	
	@Test
	void shouldSetAndGetPublicationYear() {
		int publicationYear = 1860;
		book.setPublicationYear(publicationYear);
		
		assertEquals(publicationYear, book.getPublicationYear());
	}
	
	@Test
	void shouldSetAndGetGenre() {
		String genre = "Роман";
		book.setGenre(genre);
		
		assertEquals(genre, book.getGenre());
	}
	
	@Test
	void shouldSetAndGetAddDate() {
		LocalDate addDate = LocalDate.of(2022, 2, 24);
		book.setAddDate(addDate);
		
		assertEquals(24, book.getAddDate().getDayOfMonth());
	}
}
