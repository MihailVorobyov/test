package com.example.test.services;

import com.example.test.entities.Book;
import com.example.test.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.time.LocalDate;
import java.util.*;

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
	void shouldReturnStatusOkWhenSaveNewBook() {
		Book bookBeforeSave = new Book(null, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book bookAfterSave = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Mockito.doReturn(bookAfterSave).when(bookRepositoryMock).save(bookBeforeSave);
		Response response = bookService.save(bookBeforeSave);
		assertEquals(Status.OK.getStatusCode(), response.getStatus(), "Expected statusCode is 200");
	}

	// Test save() as update exists book
	@Test
	void shouldReturnStatusOkWhenSaveExistsBook() {
		Book updated = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		
		Mockito.doReturn(true).when(bookRepositoryMock).existsById(1L);
		Mockito.doReturn(updated).when(bookRepositoryMock).save(updated);
		Response response = bookService.save(updated);
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus(), "Expected status is OK (200)");
		Book bookFromResponse = (Book) response.getEntity();
		assertEquals("Война и мир1", bookFromResponse.getTitle(), "Expect title from response entity is \"Война и мир1\"");
	}
	
	// Test save() as fail update exists book
	@Test
	void shouldReturnStatus_NOT_MODIFIED_WhenSaveExistsBook() {
		Book toUpdate = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book notUpdated = new Book(1L, "123-5-1", "Война и мир2", 1834, "Роман", LocalDate.now());
		
		Mockito.doReturn(true).when(bookRepositoryMock).existsById(1L);
		Mockito.doReturn(notUpdated).when(bookRepositoryMock).save(toUpdate);
		Response response = bookService.save(toUpdate);
		
		assertEquals(Status.NOT_MODIFIED.getStatusCode(), response.getStatus(), "Expected status is NOT_MODIFIED (304)");
	}

	// Test getAll()
	@Test
	void shouldReturnListWith4Books() {
		List<Book> booksList = new ArrayList<>(Arrays.asList(new Book(), new Book(), new Book(), new Book()));
		Mockito.doReturn(booksList).when(bookRepositoryMock).findAll();
		Collection<Book> books = (Collection<Book>) bookService.getAll().getEntity();
		assertEquals(4, books.size());
	}
	
	// Test getAllByAuthorId()
	@Test
	void shouldReturnListWith2Books() {
		Book b1 = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book b2 = new Book(2L, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now());
		Book b3 = new Book(3L, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now());
		List<Book> booksList = new ArrayList<>(Arrays.asList(b1, b2, b3));
		
		Mockito.doReturn(booksList).when(bookRepositoryMock).findAllByAuthorId(1L);
		Collection<Book> books = (Collection<Book>) bookService.getAllByAuthorId(1L).getEntity();
		assertEquals(3, books.size());
		
		Mockito.doReturn(new ArrayList<Book>()).when(bookRepositoryMock).findAllByAuthorId(2L);
		books = (Collection<Book>) bookService.getAllByAuthorId(2L).getEntity();
		assertTrue(books.isEmpty());
	}
	
	// Test getAllByTitle()
	@Test
	void shouldReturnListWith1Books() {
		Book b1 = new Book(1L, "123-5-1", "Война и мир. Том 1", 1834, "Роман", LocalDate.now());
		Book b2 = new Book(2L, "123-5-1", "Война и мир. Том 2", 1834, "Роман", LocalDate.now());
		Book b3 = new Book(3L, "123-5-1", "Война и мир. Том 3", 1834, "Роман", LocalDate.now());
		
		List<Book> booksList = new ArrayList<>(Arrays.asList(b1, b2, b3));
		
		Mockito.doReturn(booksList).when(bookRepositoryMock).findAllByTitleLike("%Война и мир%");
		Collection<Book> books = (Collection<Book>) bookService.getAllByTitle("Война и мир").getEntity();
		assertEquals(3, books.size());
		
		Mockito.doReturn(new ArrayList<Book>()).when(bookRepositoryMock).findAllByTitleLike("%Юность%");
		books = (Collection<Book>) bookService.getAllByTitle("Юность").getEntity();
		assertTrue(books.isEmpty());
	}

	// Test delete(null)
	@Test
	void shouldThrowNullPointerExceptionWhenBookToDeleteIsNull() {
		assertThrowsExactly(NullPointerException.class, () -> bookService.delete(null), "Can not delete book because it is null!");
	}

	// Test delete()
	@Test
	void shouldReturnStatusOkWhenDeleteBook() {
		Book b1 = new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book b2 = new Book(2L, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now());
		Book b3 = new Book(3L, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now());
		List<Book> books = new ArrayList<>(Arrays.asList(b1, b2, b3));
		//This book not saved;
		Book b4 = new Book(4L, "123-5-4", "Война и мир4", 1834, "Роман", LocalDate.now());
		
		Mockito.doAnswer(invocation -> books.remove(b1)).when(bookRepositoryMock).deleteById(1L);
		Mockito.doAnswer(invocation -> books.remove(b2)).when(bookRepositoryMock).deleteById(2L);
		Mockito.doAnswer(invocation -> books.remove(b3)).when(bookRepositoryMock).deleteById(3L);
		Mockito.doNothing().when(bookRepositoryMock).delete(b4);
		
		Mockito.doReturn(books.contains(b1)).when(bookRepositoryMock).existsById(1L);
		Mockito.doReturn(books.contains(b2)).when(bookRepositoryMock).existsById(2L);
		Mockito.doReturn(books.contains(b3)).when(bookRepositoryMock).existsById(3L);
		Mockito.doReturn(books.contains(b4)).when(bookRepositoryMock).existsById(4L);
		
		assertEquals(204, bookService.delete(1L).getStatus());
		assertEquals(204, bookService.delete(2L).getStatus());
		assertEquals(204, bookService.delete(3L).getStatus());
		assertNotEquals(204, bookService.delete(4L).getStatus());
		assertEquals(404, bookService.delete(4L).getStatus());
	}

	//Test get()
	@Test
	void shouldReturnBookById() {
		Optional<Book> b1 = Optional.of(new Book(1L, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now()));
		Optional<Book> b2 = Optional.of(new Book(2L, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now()));
		Optional<Book> b3 = Optional.of(new Book(3L, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now()));
		Optional<Book> b4 = Optional.empty();

		List<Optional<Book>> booksList = new ArrayList<>(Arrays.asList(b1, b2, b3, b4));

		Mockito.doReturn(booksList.get(0)).when(bookRepositoryMock).findById(1L);
		Mockito.doReturn(booksList.get(1)).when(bookRepositoryMock).findById(2L);
		Mockito.doReturn(booksList.get(2)).when(bookRepositoryMock).findById(3L);
		Mockito.doReturn(booksList.get(3)).when(bookRepositoryMock).findById(4L);
		
		Book book1 = (Book) bookService.get(1L).getEntity();
		Book book2 = (Book) bookService.get(2L).getEntity();
		Book book3 = (Book) bookService.get(3L).getEntity();
		Book book4 = (Book) bookService.get(4L).getEntity();
		
		assertEquals(b1.get(), book1);
		assertEquals(b2.get(), book2);
		assertEquals(b3.get(), book3);
		assertNull(book4.getTitle());
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
		Response response = bookService.saveAll(booksBeforeSaving);
		List<Book> result = (List<Book>) response.getEntity();
		assertEquals("Война и мир1", result.get(0).getTitle());
		assertEquals("Война и мир2", result.get(1).getTitle());
		assertEquals("Война и мир3", result.get(2).getTitle());
	}
	
	
}
