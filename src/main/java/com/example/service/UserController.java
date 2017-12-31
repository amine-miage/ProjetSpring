package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.DemandeRepository;
import com.example.dao.UserRepository;
import com.example.entity.DemandeAbonnement;
import com.example.entity.User;
import com.example.metier.UserMetier;



@Controller
@RequestMapping(value = "/accueil")
public class UserController {

	@Autowired
	UserRepository ur;
	
	@Autowired
    private UserMetier urr;
	
	@Autowired
    DemandeRepository dr;
	
	@RequestMapping(value = "/inscription", method = RequestMethod.GET)
	public String forminscription(Model model) {
		model.addAttribute("user", new User());
		return "inscription";
}

	@RequestMapping(value = "/inscriptionUser", method = RequestMethod.POST)
	public String SaveUser(User s) {
		s.setRole("CLIENT");
		s.setActive(false);
		s.setExpirer(false);
		ur.save(s);
		DemandeAbonnement demande = new DemandeAbonnement(1,"waiting","demande d`abonnement de "+" "+s.getMail(),s);
		demande.toString();
		demande.setCat("Abonnement");
		dr.save(demande);
		return "redirect:/accueil";
	}
	
}
