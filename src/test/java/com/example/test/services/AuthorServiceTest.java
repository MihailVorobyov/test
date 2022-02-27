package com.example.test.services;

import com.example.test.entities.Author;
import com.example.test.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorServiceTest {
	
	@Autowired
	AuthorService authorService;
	
	@MockBean
	AuthorRepository authorRepositoryMock;
	
	// Test save() with null
	@Test
	void shouldThrowNullPointerExceptionWhenAuthorIsNull() {
		assertThrowsExactly(NullPointerException.class, () -> authorService.save(null));
	}
	
	// Test save() with new author
	@Test
	void shouldReturnStatusOkWhenSaveNewAuthor() {
		Author authorBeforeSave = new Author(null, "Фёдор", "Михайлович", "Достоевский");
		Author authorAfterSave = new Author(1L, "Фёдор", "Михайлович", "Достоевский");
		Mockito.doReturn(authorAfterSave).when(authorRepositoryMock).save(authorBeforeSave);
		Response response = authorService.save(authorBeforeSave);
		assertEquals(Status.OK.getStatusCode(), response.getStatus(), "Expected statusCode is 200");
	}
	
	// Test save() as update exists author
	@Test
	void shouldReturnStatusOkWhenSaveExistsAuthor() {
		Author updated = new Author(1L, "Fedor", "Михайлович", "Достоевский");
		
		Mockito.doReturn(true).when(authorRepositoryMock).existsById(1L);
		Mockito.doReturn(updated).when(authorRepositoryMock).save(new Author(1L, "Fedor", "Михайлович", "Достоевский"));
		Response response = authorService.save(updated);
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus(), "Expected status is OK (200)");
		Author authorFromResponse = (Author) (response.getEntity());
		assertEquals("Fedor", authorFromResponse.getFirstName(), "Expect firstName from response entity is \"Fedor\"");
	}
	
	// Test save() as fail update exists author
	@Test
	void shouldReturnStatus_NOT_MODIFIED_WhenSaveExistsAuthor() {
		Author toUpdate = new Author(1L, "Fedor", "Михайлович", "Достоевский");
		Author notUpdated = new Author(1L, "Фёдор", "Михайлович", "Достоевский");
		
		Mockito.doReturn(true).when(authorRepositoryMock).existsById(1L);
		Mockito.doReturn(notUpdated).when(authorRepositoryMock).save(toUpdate);
		Response response = authorService.save(toUpdate);
		
		assertEquals(Status.NOT_MODIFIED.getStatusCode(), response.getStatus(), "Expected status is NOT_MODIFIED (304)");
	}
	

	//Test getAll()
	@Test
	void shouldReturnListWith4Authors() {
		List<Author> authorList = new ArrayList<>(Arrays.asList(new Author(), new Author(), new Author(), new Author()));
		Mockito.doReturn(authorList).when(authorRepositoryMock).findAll();
		Collection<Author> authors = (Collection<Author>) authorService.getAll().getEntity();
		assertEquals(4, authors.size());
	}

	// Test delete()
	@Test
	void shouldReturnStatusOkWhenDeleteAuthor() {
		Author a1 = new Author(1L, "01", "02", "03");
		Author a2 = new Author(2L, "11", "12", "13");
		Author a3 = new Author(3L, "21", "22", "23");
		List<Author> authors = new ArrayList<>(Arrays.asList(a1, a2, a3));
		//This author not saved;
		Author a4 = new Author(4L, "31", "32", "33");

		Mockito.doAnswer(invocation -> authors.remove(a1)).when(authorRepositoryMock).deleteById(1L);
		Mockito.doAnswer(invocation -> authors.remove(a2)).when(authorRepositoryMock).deleteById(2L);
		Mockito.doAnswer(invocation -> authors.remove(a3)).when(authorRepositoryMock).deleteById(3L);
		Mockito.doNothing().when(authorRepositoryMock).delete(a4);

		Mockito.doReturn(authors.contains(a1)).when(authorRepositoryMock).existsById(1L);
		Mockito.doReturn(authors.contains(a2)).when(authorRepositoryMock).existsById(2L);
		Mockito.doReturn(authors.contains(a3)).when(authorRepositoryMock).existsById(3L);
		Mockito.doReturn(authors.contains(a4)).when(authorRepositoryMock).existsById(4L);

		assertEquals(204, authorService.delete(1L).getStatus());
		assertEquals(204, authorService.delete(2L).getStatus());
		assertEquals(204, authorService.delete(3L).getStatus());
		assertNotEquals(204, authorService.delete(4L).getStatus());
		assertEquals(404, authorService.delete(4L).getStatus());
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

		Author author1 = (Author) authorService.get(1L).getEntity();
		Author author2 = (Author) authorService.get(2L).getEntity();
		Author author3 = (Author) authorService.get(3L).getEntity();
		Author author4 = (Author) authorService.get(4L).getEntity();
		
		assertEquals(a1.get(), author1);
		assertEquals(a2.get(), author2);
		assertEquals(a3.get(), author3);
		assertNull(author4.getFirstName());
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
		Response response = authorService.saveAll(authorsBeforeSaving);
		List<Author> result = (List<Author>) response.getEntity();
		assertEquals("1", result.get(0).getFirstName());
		assertEquals("2", result.get(1).getMiddleName());
		assertEquals("3", result.get(2).getLastName());
	}
}
