package com.example.test.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
	
	@Test
	void shouldSetAuthor() {
		Book b1 = new Book(1L, "123-5-1", "Война и мир1", 1867, "Роман", LocalDate.now());
		Author a1 = new Author(1L, "Лев", "Николаевич", "Толстой");
		a1.addBook(b1);
		assertEquals(b1, a1.getBooks().get(0));
		assertEquals(a1, b1.getAuthor());
	}
	
	@Test
	void shouldReturnEqualsIfBooksEquals() {
		Book b1 = new Book(null, "123-5-1", "Война и мир1", 1867, "Роман", LocalDate.now());
		Book b2 = b1;
		Book b3 = new Book(1L, "123-5-1", "Война и мир1", 1867, "Роман", LocalDate.of(2022,2,1));
		
		assertEquals(b1, b1);
		assertEquals(b1, b2);
		assertEquals(b2,b1);
		assertNotEquals(null, b1);
		assertNotEquals(new Book(null, "123-5-1", "Война и мир", 1867, "Роман", LocalDate.now()), b1);
	}
	
	@Test
	void shouldReturnSameHashCodeIfBooksEquals() {
		Book b1 = new Book(null, "123-5-1", "Война и мир1", 1867, "Роман", LocalDate.now());
		Book b2 = new Book(1L, "123-5-1", "Война и мир1", 1867, "Роман", LocalDate.of(2022,2,1));
		
		assertEquals(b1.hashCode(), b2.hashCode());
		for (int i = 0; i < 15; i++) {
			assertEquals(b1.hashCode(),
				new Book((long) i, "123-5-1", "Война и мир1", 1867, "Роман", LocalDate.of(2022,2,i + 1)).hashCode());
		}
		
		b2.setTitle("Война и мир");
		assertNotEquals(b1.hashCode(), b2.hashCode());
	}
}
