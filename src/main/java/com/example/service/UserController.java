package com.example.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.UserRepository;
import com.example.entity.User;
import com.example.metier.UserMetier;



@Controller
@RequestMapping(value = "/accueil")
public class UserController {

	@Autowired
	UserRepository ur;
	
	@Autowired
    private UserMetier urr;
	
	@RequestMapping(value = "/inscription", method = RequestMethod.GET)
	public String forminscription(Model model) {
		model.addAttribute("user", new User());
		return "inscription";
}

	@RequestMapping(value = "/inscriptionUser", method = RequestMethod.POST)
	public String SaveUser(User s) {
		s.setRole("client");
		s.setActive(false);
		s.setExpirer(false);
		ur.save(s);
		return "redirect:/accueil";
	}
	
}
