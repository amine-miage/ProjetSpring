
package com.example.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dao.UserRepository;
import com.example.entity.User;

@Controller

public class EmployeController{

	
	@Autowired
	UserRepository ur;
	
	@RequestMapping(value="/gestionClient",method=RequestMethod.GET)
	public String gererClient(Model model ,
			@RequestParam(name="motCle" , defaultValue="" )String mc){
		
		List<User> users = ur.chercherClient("%"+mc+"%");
		model.addAttribute("users",users);
		return "gestionClient";
	}
	


	@RequestMapping(value="/editerClient",method=RequestMethod.GET)
	public String edit(Model model , @RequestParam(value="userId")int id){
		User  u = ur.findById(id);
		
		model.addAttribute("u",u);
		
		return "EditFormClient";
	}
	
	@RequestMapping(value = "/EditClient", method = RequestMethod.POST)
	public String SaveUser(User s) {
		s.setRole("client");
		//s.setActive(false);
		s.setExpirer(false);
		ur.save(s);
		return "redirect:/gestionClient";
	}

	@Transactional
	@RequestMapping(value="/DeleteClient", method=RequestMethod.GET)
	public String deleteClient(@RequestParam(value="userId") int id){
	
		ur.deleteById(id);
		
		return "redirect:/gestionClient";
	}	
}
