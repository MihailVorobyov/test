package com.example.test.resources;

import com.example.test.entities.Author;
import com.example.test.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authors")
public class AuthorResource {
	
	private AuthorService authorService;
	
	@Autowired
	public void setAuthorService(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAuthors() {
		return authorService.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAuthor(@RequestBody Author author) {
		return authorService.save(author);
	}
	
	@DELETE
	public Response deleteAuthorById(@QueryParam("id") Long id) {
		return authorService.delete(id);
	}
}
