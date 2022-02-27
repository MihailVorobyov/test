package com.example.test.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
	
	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	private List<Book> books = new ArrayList<>();
	
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
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Author author = (Author) o;
		return this.firstName.equals(author.firstName) && this.middleName.equals(author.middleName)
			&& this.lastName.equals(author.lastName) && this.books.equals(author.books);
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
