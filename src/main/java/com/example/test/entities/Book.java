package com.example.test.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
//
//	@Column(name = "authors")
//	private Long author_id;
	
	@Column(name = "ISBN")
	private String ISBN;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "publication_year")
	private Integer publicationYear;
	
	@Column(name = "genre")
	private String genre;
	
	@Column(name = "add_date")
	private LocalDate addDate;
	
	@OneToMany(mappedBy = "books")
	private Set<Author> authors;
	
	public Book(Long id, String ISBN, String title, Integer publicationYear, String genre, LocalDate addDate) {
		this.id = id;
//		this.author_id = author_id;
		this.ISBN = ISBN;
		this.title = title;
		this.publicationYear = publicationYear;
		this.genre = genre;
		this.addDate = addDate;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Book book = (Book) o;
		return id != null && Objects.equals(id, book.id);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
