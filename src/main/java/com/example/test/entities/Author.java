package com.example.test.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@OneToMany(mappedBy = "author")
	private List<Book> books;
	
	public void addBook(Book book) {
		if (book == null || book.getId() == null) {
			throw new NullPointerException("Wrong book!");
		}
		if (book.getAuthor() != null) {
			throw  new IllegalArgumentException("Book already has a author!");
		}
		book.setAuthor(this);
		this.books.add(book);
	}
	
	public Author(Long id, String firstName, String middleName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.books = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Author author = (Author) o;
		return id != null && Objects.equals(id, author.id);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	@Override
	public String toString() {
		return "Author{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", middleName='" + middleName + '\'' +
			", lastName='" + lastName + '\'' +
			'}';
	}
}
