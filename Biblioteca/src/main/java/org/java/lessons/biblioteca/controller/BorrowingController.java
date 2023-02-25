package org.java.lessons.biblioteca.controller;

import java.util.List;

import javax.validation.Valid;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Borrowing;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.java.lessons.biblioteca.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

	@Autowired
	BorrowingRepository borrowingRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@GetMapping
	public String index(			
			Model model) {
		List<Borrowing> res;
		
		
		res=borrowingRepository.findAll(Sort.by("borrowingDate"));
		
		model.addAttribute("elencoPrestiti", res);
		return "borrowings/index";
	}
	
	@GetMapping("/create")
	public String create(
			@RequestParam(name="bookId", required=true) Integer bookId, 
			Model model) {
		Borrowing borrowing=new Borrowing();
		Book book=bookRepository.getById(bookId);
		
		borrowing.setBook(book);
		
	
		model.addAttribute("borrowing", borrowing);

		return "borrowings/create";
	}
	
	@PostMapping("/create")
	public String store(
		@Valid @ModelAttribute("borrowing") Borrowing formBorrowing, 
		BindingResult bindingResult,
		Model model){
		
		if(bindingResult.hasErrors()) 
			return "borrowings/create";
		Book book=formBorrowing.getBook();
		book.setAvailableCopies(book.getAvailableCopies()-1);		//in service
		//bookRepository.save(book);
		borrowingRepository.save(formBorrowing);
	    return "redirect:/borrowings";
	}
	
}
