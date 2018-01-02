package com.example.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dao.MediaRepository;
import com.example.dao.UserRepository;
import com.example.entity.Media;
import com.example.entity.User;

@Controller
public class AccueilController {

	@Autowired
	MediaRepository mr;
	
	@Autowired
	UserRepository ur;
    @RequestMapping("/accueil")
    public String accueil() {
        
        return "accueil";
    }
    
    @RequestMapping("/login")
    public String login() {
        
        return "login";
    }
    
	
	@RequestMapping(value = "/authentification")
	public String handleRequest(HttpServletRequest httpServletRequest, Model model) throws Exception {
		List<Media> medias = mr.findAll();
		model.addAttribute("medias",medias);
		
		//model.addAttribute("users",users);
		HttpSession httpSession = httpServletRequest.getSession();
		
		SecurityContext securityContext=(SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		
		String username = securityContext.getAuthentication().getName();
		
		User u =ur.findByMail(username);
		//int iduser=u.getId();
		model.addAttribute("u",u);
		List<String> roles = new ArrayList<>();
		 for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()) {
   	      roles.add(ga.getAuthority()); 
		 }
   	      for (String r : roles) {
   	    	  if(r.toString().toUpperCase().compareTo("ROLE_ADMIN")==0) {
   	    		 return "AdminPage";
   	      }else if(r.toString().compareTo("ROLE_EMPLOYE")==0) {
   	    	  return "EmployePage";
   	      }      
     }
   	     // if(u.getAbonement().equals("abonné")){
   	      return "ClientPage";
   	      //}
   	      
   	      //else
   	    	//  return "NonAbonne";
   	      
}


	
}