package org.java.lessons.biblioteca.controller;

import java.util.List;

import javax.validation.Valid;

import org.java.lessons.biblioteca.model.Book;
import org.java.lessons.biblioteca.model.Borrowing;
import org.java.lessons.biblioteca.repository.BookRepository;
import org.java.lessons.biblioteca.repository.BorrowingRepository;
import org.java.lessons.biblioteca.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BorrowingRepository borrowingRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping
	public String index(
			@RequestParam(name="keyword", required=false) String keyword, 
			Model model) {
		List<Book> res;
		
		if(keyword != null && !keyword.isEmpty()) 
			res=bookRepository.findByTitleLike("%"+keyword+"%");
		else
			res=bookRepository.findAll(Sort.by("title"));
		
		model.addAttribute("elencoLibri", res);
		return "books/index";
	}

	@GetMapping("/{id}")	
	public String detail(@PathVariable("id") Integer id, Model model) {
		Book book=bookRepository.getById(id);
		List<Borrowing> borrowingsNotReturned=borrowingRepository.findByReturnDateIsNullAndBook(book);
		//System.out.println(book.getAuthor());
		//System.out.println(book.getBorrowings().get(0).getBorrowingDate());
		model.addAttribute("book", book);
		model.addAttribute("copiedisp", book.getAvailableCopies()-borrowingsNotReturned.size());
		return "books/detail";
	}
	
	@GetMapping("/create")	
	public String create(Model model) {
		Book book=new Book();
	
		model.addAttribute("book", book);

		return "books/create";
	}
	
	
	@PostMapping("/create")
	public String store(
		@Valid @ModelAttribute("book") Book formBook, 
		BindingResult bindingResult,
		Model model){
		
		if(bindingResult.hasErrors()) 
			return "books/create";
		    
		bookRepository.save(formBook);
	    return "redirect:/books";
	}
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("book", bookRepository.getById(id));
		model.addAttribute("categoryList", categoryRepository.findAll());
		return "/books/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("book") Book formBook, 
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			return "/books/edit";
		}
		
		//formBook.setUpdatedAt(LocalDateTime.now());
		
		bookRepository.save(formBook);
		
		redirectAttributes.addFlashAttribute("successMessage", "Book edited!");
		
		return "redirect:/books";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {

		bookRepository.deleteById(id);
	   
	   return "redirect:/books";
	}
}
