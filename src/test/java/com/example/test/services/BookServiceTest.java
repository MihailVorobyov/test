package com.example.test.services;

import com.example.test.entities.Book;
import com.example.test.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceTest {
	@Autowired
	BookService bookService;
	
	@MockBean
	BookRepository bookRepositoryMock;
	
	// Test save() with null
	@Test
	void shouldThrowNullPointerExceptionWhenBookIsNull() {
		Book book = null;
		assertThrowsExactly(NullPointerException.class, () -> bookService.save(book));
	}
	
	// Test save() with new book
	@Test
	void shouldReturnTrueWhenSaveNewBook() {
		Book bookBeforeSave = new Book(null, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book bookAfterSave = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Mockito.doReturn(bookAfterSave).when(bookRepositoryMock).save(bookBeforeSave);
		boolean isSaved = bookService.save(bookBeforeSave);
		assertTrue(isSaved);
	}
	
	// Test save() as update exists book
	@Test
	void shouldReturnTrueWhenSaveExistsBook() {
		Book bookBeforeSave = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Mockito.doReturn(new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now())).when(bookRepositoryMock).save(bookBeforeSave);
		assertTrue(bookService.save(bookBeforeSave));
	}
	
	// Test getAll()
	@Test
	void shouldReturnListWith4Books() {
		List<Book> authorList = new ArrayList<>(Arrays.asList(new Book(), new Book(), new Book(), new Book()));
		Mockito.doReturn(authorList).when(bookRepositoryMock).findAll();
		assertEquals(4, bookService.getAll().size());
	}

	// Test delete(null)
	@Test
	void shouldThrowNullPointerExceptionWhenBookToDeleteIsNull() {
		assertThrowsExactly(NullPointerException.class, () -> bookService.delete(null), "Can not delete book because it is null!");
	}
	
	// Test delete()
	@Test
	void shouldReturnTrueWhenDeleteBook() {
		Book a1 = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book a2 = new Book(2L, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now());
		Book a3 = new Book(3L, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now());

		//This book not saved;
		Book a4 = new Book(4L, "123-5-4", "Война и мир4", 1834, "Роман", LocalDate.now());

		Mockito.doNothing().when(bookRepositoryMock).deleteById(1L);
		Mockito.doReturn(true).when(bookRepositoryMock).existsById(1L);
		assertTrue(bookService.delete(a1));

		Mockito.doNothing().when(bookRepositoryMock).deleteById(2L);
		Mockito.doReturn(true).when(bookRepositoryMock).existsById(2L);
		assertTrue(bookService.delete(a2));

		Mockito.doNothing().when(bookRepositoryMock).deleteById(3L);
		Mockito.doReturn(true).when(bookRepositoryMock).existsById(3L);
		assertTrue(bookService.delete(a3));

		Mockito.doNothing().when(bookRepositoryMock).delete(a4);
		Mockito.doReturn(false).when(bookRepositoryMock).existsById(4L);
		assertFalse(bookService.delete(a4));
	}

	//Test get()
	@Test
	void shouldReturnBookById() {
		Optional<Book> b1 = Optional.of(new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now()));
		Optional<Book> b2 = Optional.of(new Book(2L, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now()));
		Optional<Book> b3 = Optional.of(new Book(3L, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now()));
		Optional<Book> b4 = Optional.empty();

		List<Optional<Book> > authorList = new ArrayList<>(Arrays.asList(b1, b2, b3, b4));

		Mockito.doReturn(authorList.get(0)).when(bookRepositoryMock).findById(1L);
		Mockito.doReturn(authorList.get(1)).when(bookRepositoryMock).findById(2L);
		Mockito.doReturn(authorList.get(2)).when(bookRepositoryMock).findById(3L);
		Mockito.doReturn(authorList.get(3)).when(bookRepositoryMock).findById(4L);

		assertEquals(b1.get(), bookService.get(1L));
		assertEquals(b2.get(), bookService.get(2L));
		assertEquals(b3.get(), bookService.get(3L));
		assertNull(bookService.get(4L));
	}

	//Test saveAll()
	@Test
	void shouldReturnListWith3BooksWithIdsAreNotNull() {
		Book a1 = new Book(null, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book a2 = new Book(null, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now());
		Book a3 = new Book(null, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now());
		List<Book> booksBeforeSaving = new ArrayList<>(Arrays.asList(a1, a2, a3));

		Book a11 = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book a22 = new Book(2L, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now());
		Book a33 = new Book(3L, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now());
		List<Book> booksAfterSaving = new ArrayList<>(Arrays.asList(a11, a22, a33));

		Mockito.when(bookRepositoryMock.saveAll(booksBeforeSaving)).thenReturn(booksAfterSaving);
		List<Book> result = bookService.saveAll(booksBeforeSaving);
		
		assertEquals("Война и мир1", result.get(0).getTitle());
		assertEquals("Война и мир2", result.get(1).getTitle());
		assertEquals("Война и мир3", result.get(2).getTitle());
	}
}
