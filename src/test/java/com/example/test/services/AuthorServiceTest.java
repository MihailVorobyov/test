package com.example.test.services;

import com.example.test.entities.Author;
import com.example.test.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthorServiceTest {
	
	@Autowired
	AuthorService authorService;
	
	@MockBean
	AuthorRepository authorRepositoryMock;
	
	// Test save() with null
	@Test
	void shouldThrowNullPointerExceptionWhenAuthorIsNull() {
		Author author = null;
		assertThrowsExactly(NullPointerException.class, () -> authorService.save(author));
	}
	
	// Test save() with new author
	@Test
	void shouldReturnTrueWhenSaveNewAuthor() {
		Author authorBeforeSave = new Author(null, "Фёдор", "Михайлович", "Достоевский");
		Author authorAfterSave = new Author(1L, "Фёдор", "Михайлович", "Достоевский");
		Mockito.doReturn(authorAfterSave).when(authorRepositoryMock).save(authorBeforeSave);
		boolean isSaved = authorService.save(authorBeforeSave);
		assertTrue(isSaved);
	}
	
	// Test save() as update exists author
	@Test
	void shouldReturnFalseWhenSaveExistsAuthor() {
		Author authorExists = new Author(1L, "Фёдор", "Михайлович", "Достоевский");
		assertFalse(authorService.save(authorExists));
	}
	
	// Test getAll()
	@Test
	void shouldReturnListWith4Authors() {
		List<Author> authorList = new ArrayList<>(Arrays.asList(new Author(), new Author(), new Author(), new Author()));
		Mockito.doReturn(authorList).when(authorRepositoryMock).findAll();
		assertEquals(4, authorService.getAll().size());
	}
	
	// Test delete()
	@Test
	void shouldReturnTrueWhenDeleteAuthor() {
		Author a1 = new Author(1L, "01", "02", "03");
		Author a2 = new Author(2L, "11", "12", "13");
		Author a3 = new Author(3L, "21", "22", "23");
		
		//This author not saved;
		Author a4 = new Author(4L, "31", "32", "33");
		
		Mockito.doNothing().when(authorRepositoryMock).deleteById(1L);
		Mockito.doReturn(true).when(authorRepositoryMock).existsById(1L);
		assertTrue(authorService.delete(a1));
		
		Mockito.doNothing().when(authorRepositoryMock).deleteById(2L);
		Mockito.doReturn(true).when(authorRepositoryMock).existsById(2L);
		assertTrue(authorService.delete(a2));
		
		Mockito.doNothing().when(authorRepositoryMock).deleteById(3L);
		Mockito.doReturn(true).when(authorRepositoryMock).existsById(3L);
		assertTrue(authorService.delete(a3));
		
		Mockito.doNothing().when(authorRepositoryMock).delete(a4);
		Mockito.doReturn(false).when(authorRepositoryMock).existsById(4L);
		assertFalse(authorService.delete(a4));
	}
	
	//Test get()
	@Test
	void shouldReturnAuthorById() {
		Optional<Author>  a1 = Optional.of((new Author(1L, "1", "1", "1")));
		Optional<Author>  a2 = Optional.of(new Author(2L, "2", "2", "2"));
		Optional<Author>  a3 = Optional.of(new Author(3L, "3", "3", "3"));
		Optional<Author>  a4 = Optional.empty();
		
		List<Optional<Author> > authorList = new ArrayList<>(Arrays.asList(a1, a2, a3, a4));
		
		Mockito.doReturn(authorList.get(0)).when(authorRepositoryMock).findById(1L);
		Mockito.doReturn(authorList.get(1)).when(authorRepositoryMock).findById(2L);
		Mockito.doReturn(authorList.get(2)).when(authorRepositoryMock).findById(3L);
		Mockito.doReturn(authorList.get(3)).when(authorRepositoryMock).findById(4L);
		
		assertEquals(a1.get(), authorService.get(1L));
		assertEquals(a2.get(), authorService.get(2L));
		assertEquals(a3.get(), authorService.get(3L));
		assertNull(authorService.get(4L));
	}
	
	//Test saveAll()
	@Test
	void shouldReturnListWith3AuthorsWithIdsAreNotNull() {
		Author a1 = new Author(null, "1", "1", "1");
		Author a2 = new Author(null, "2", "2", "2");
		Author a3 = new Author(null, "3", "3", "3");
		List<Author> authorsBeforeSaving = new ArrayList<>(Arrays.asList(a1, a2, a3));
		
		Author a11 = new Author(1L, "1", "1", "1");
		Author a22 = new Author(2L, "2", "2", "2");
		Author a33 = new Author(3L, "3", "3", "3");
		List<Author> authorsAfterSaving = new ArrayList<>(Arrays.asList(a11, a22, a33));
		
		Mockito.when(authorRepositoryMock.saveAll(authorsBeforeSaving)).thenReturn(authorsAfterSaving);
		List<Author> result = authorService.saveAll(authorsBeforeSaving);
		assertEquals("1", result.get(0).getFirstName());
		assertEquals("2", result.get(1).getMiddleName());
		assertEquals("3", result.get(2).getLastName());
	}
}
