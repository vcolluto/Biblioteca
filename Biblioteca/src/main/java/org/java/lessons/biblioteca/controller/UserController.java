package org.java.lessons.biblioteca.controller;

import org.java.lessons.biblioteca.model.User;
import org.java.lessons.biblioteca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping()
	@ResponseBody
	public String index() {
		User u=userRepository.findByUsername("enzo").get();
		return "utente: "+ u.getUsername();
	}
}
