package com.example.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dao.UserRepository;
import com.example.entity.User;
import com.example.metier.UserMetier;

@Controller
public class loginController {
	
	
	
	@Autowired
	UserRepository ur;
	
	@Autowired
    private UserMetier urr;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String formlogin(Model model) {	
		 model.addAttribute("user", new User());
		return "login";
	}

@RequestMapping(value ="/loginuser", method = RequestMethod.POST)
	public String authentification(@Valid @ModelAttribute("user") User s) {
	   String mail = s.getMail();
	   String password = s.getPassword();
		User user = urr.findUser(mail, password);
		if (user != null) {
			if (user.getRole().equals("admin")) {
				return "redirect:/Admin/gestionEmploye";
			} else if (user.getRole().equals("Employe")) {
				return "administrationEmploye";
			}
		}
		return "redirect:/accueil";

	}

	

}
