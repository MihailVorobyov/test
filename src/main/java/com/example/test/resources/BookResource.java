package com.example.test.resources;

import com.example.test.entities.Book;
import com.example.test.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/books")
public class BookResource {
	
	private BookService bookService;

	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBooks() {
		return bookService.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(@RequestBody Book book) {
		return bookService.save(book);
	}
	
	@DELETE
	public Response deleteBook(@QueryParam("id") Long id) {
		return bookService.delete(id);
	}
	
	@GET
	@Path("/find/byAuthorId/{author_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByAuthorId(@PathParam("author_id") Long authorId) {
		System.out.println("authorId = " + authorId);
		return bookService.getAllByAuthorId(authorId);
	}
	
	@GET
	@Path("/find/byTitle/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByTitle( @PathParam("title") String title) {
		System.out.println("title = " + title);
		return bookService.getAllByTitle(title);
	}
	
}
