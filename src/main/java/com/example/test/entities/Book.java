package com.example.test.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "author")
	@ManyToMany
	@JoinTable(
		name = "books_authors",
		joinColumns = @JoinColumn(name = "book_id"),
		inverseJoinColumns = @JoinColumn(name = "author_id"))
	private List<Author> authors;
	
	@Column(name = "ISBN")
	private String ISBN;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "genre")
	private String genre;
	
	@Column(name = "addDate")
	private Date addDate;
	
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
