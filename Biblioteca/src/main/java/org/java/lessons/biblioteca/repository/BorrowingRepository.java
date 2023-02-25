package org.java.lessons.biblioteca.repository;

import java.util.List;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer>{
	
	public List<Borrowing> findByReturnDateIsNullAndBook(Book book);
}
