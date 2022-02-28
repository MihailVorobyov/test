package com.example.test.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ISBN")
	private String ISBN;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "publication_year")
	private Integer publicationYear;
	
	@Column(name = "genre")
	private String genre;
	
	@Column(name = "add_date", updatable = false)
	@CreationTimestamp
	private LocalDate addDate;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
	
	public Book(Long id, String ISBN, String title, Integer publicationYear, String genre, LocalDate addDate) {
		this.id = id;
		this.ISBN = ISBN;
		this.title = title;
		this.publicationYear = publicationYear;
		this.genre = genre;
		this.addDate = addDate;
	}
	
	public Book(Long id, String ISBN, String title, Integer publicationYear, String genre, LocalDate addDate, Author author) {
		this.id = id;
		this.ISBN = ISBN;
		this.title = title;
		this.publicationYear = publicationYear;
		this.genre = genre;
		this.addDate = addDate;
		this.author = author;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Book book = (Book) o;
		return this.ISBN.equals(book.getISBN()) && this.title.equals(book.getTitle())
			&& this.publicationYear.equals(book.getPublicationYear()) && this.genre.equals(book.getGenre());
	}
	
	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		result = 5 * result + ISBN.hashCode();
		result = 5 * result + title.hashCode();
		result = 5 * result + publicationYear.hashCode();
		result = 5 * result + genre.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return "Book{" +
			"id=" + id +
			", ISBN='" + ISBN + '\'' +
			", title='" + title + '\'' +
			", publicationYear=" + publicationYear +
			", genre='" + genre + '\'' +
			", addDate=" + addDate +
			", author=" + author +
			'}';
	}
}
