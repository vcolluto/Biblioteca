package org.java.lessons.biblioteca.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name="books")
public class Book {
	//@PersistenceContext
    //private EntityManager entityManager;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="title must not be null")
	@NotEmpty(message="title must not be empty")
	private String title;
	
	@NotNull(message="author must not be null")
	@NotEmpty(message="author must not be empty")
	private String author;
	
	@NotNull(message="isbn must not be null")
	@Size(min=13, max=13, message="isbn must be 13 charachters")
	@Column(name="isbn_code")
	private String isbn;
	
	@NotNull(message="available copies must not be null")
	@PositiveOrZero(message="available copies must greater or equal to zero")
	private Integer availableCopies;
	
	@OneToMany(mappedBy = "book", cascade = { CascadeType.REMOVE } )
	private List<Borrowing> borrowings;
	
	@ManyToMany()	
	private List<Category> categories;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getId() {
		return id;
	}
	public List<Borrowing> getBorrowings() {
		return borrowings;
	}
	public void setBorrowings(List<Borrowing> borrowings) {
		this.borrowings = borrowings;
	}
	public Integer getAvailableCopies() {
		return availableCopies;
	}
	public void setAvailableCopies(Integer availableCopies) {
		this.availableCopies = availableCopies;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	/*
	public Integer getCopieDisponibili() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		EntityManager em = session.getEntityManagerFactory().createEntityManager();
		
         TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(b) FROM Borrowing b "
            + "WHERE b.book = :book "
            + "AND b.returnDate IS NULL",
            Long.class
        );
        query.setParameter("book", this);
        Long prestitiNonRestituiti = query.getSingleResult();
        
        return availableCopies - prestitiNonRestituiti.intValue();
    }*/
}
