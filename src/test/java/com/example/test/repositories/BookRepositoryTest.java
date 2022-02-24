package com.example.test.repositories;

import com.example.test.entities.Author;
import com.example.test.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	@BeforeEach
	void setUp() {
		// Удалить из таблицы все книги
		bookRepository.deleteAll();
		authorRepository.deleteAll();
	}
	
	@Test
	void shouldAddBookAndReturnItWithId() {
		
		assertTrue(bookRepository.findAll().isEmpty());
		
		Book book1 = new Book(null, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		assertNotNull(bookRepository.save(book1).getId());
		
		Book book2 = new Book(null, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now());
		Book book3 = new Book(null, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now());
		
		Book book4 = new Book(null, "123-5-4", "Сказки1", 1850, "Сказки", LocalDate.now());
		Book book5 = new Book(null, "123-5-5", "Сказки2", 1850, "Сказки", LocalDate.now());
		
		bookRepository.saveAll(Arrays.asList(book2, book3, book4, book5));
		List<Book> books = bookRepository.findAll();
		int booksWithIdNotNull = (int) books.stream()
				.filter(b -> b.getId() != null)
					.count();
		assertEquals(5, booksWithIdNotNull);
	}
	
	@Test
	void shouldDeleteBookById() {
		
		assertTrue(bookRepository.findAll().isEmpty());
		
		//Добавить 3 книги
		Book book1 = new Book(null, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now());
		Book book2 = new Book(null, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now());
		Book book3 = new Book(null, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now());
		bookRepository.saveAll(Arrays.asList(book1, book2, book3));
		List<Book> books = bookRepository.findAll();
		assertEquals(3, books.size());
		
		// удалить книгу с указанным id
		Book bookToDelete = books.iterator().next();
		long idToDelete = bookToDelete.getId();
		assertTrue(bookRepository.existsById(idToDelete));
		bookRepository.deleteById(idToDelete);
		assertFalse(bookRepository.existsById(idToDelete));
	}
	
	@Test
	void shouldReturnAllBooksByAuthor() {
		
		assertTrue(bookRepository.findAll().isEmpty());
		
		Author aPushkin = new Author(null, "Александр", "Сергеевич", "Пушкин");
		Author lTolstoy = new Author(null, "Лев", "Николаевич", "Толстой");
		
		Book book1 = bookRepository.save(new Book(null, "123-5-1", "Война и мир1", 1834, "Роман", LocalDate.now()));
		Book book2 = bookRepository.save(new Book(null, "123-5-2", "Война и мир2", 1834, "Роман", LocalDate.now()));
		Book book3 = bookRepository.save(new Book(null, "123-5-3", "Война и мир3", 1834, "Роман", LocalDate.now()));
		Book book4 = bookRepository.save(new Book(null, "123-5-4", "Сказки1", 1850, "Сказки", LocalDate.now()));
		Book book5 = bookRepository.save(new Book(null, "123-5-5", "Сказки2", 1850, "Сказки", LocalDate.now()));
		
		lTolstoy.addBook(book1);
		lTolstoy.addBook(book2);
		lTolstoy.addBook(book3);
		
		aPushkin.addBook(book4);
		aPushkin.addBook(book5);
		
		aPushkin = authorRepository.save(aPushkin);
		lTolstoy = authorRepository.save(lTolstoy);
		
		List<Author> authors = authorRepository.saveAll(Arrays.asList(lTolstoy, aPushkin));
		
		List<Book> pushkinBooks = bookRepository.findAllByAuthor(aPushkin);
		assertEquals(2, pushkinBooks.size());
		
		List<Book> tolstoyBooks = bookRepository.findAllByAuthor(lTolstoy);
		assertEquals(3, tolstoyBooks.size());
		
		// Получение книг по названию
		List<Book> books = bookRepository.findOneByTitle("Война и мир1");
		assertEquals(1, books.size());
	}
}
