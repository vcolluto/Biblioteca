package org.java.lessons.biblioteca.repository;

import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>{
	
	//possiamo definire delle query custom
	   public List<Book> findByTitleLike(String title);
		   
	 //  public List<Book> findByTitleOrAuthor(String title, String author);
}
