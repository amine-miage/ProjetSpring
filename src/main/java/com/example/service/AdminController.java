package com.example.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/Admin")
public class AdminController {

	
	@Autowired
	UserRepository ur;
	
	@RequestMapping(value="/gestionEmploye",method=RequestMethod.GET)
	public String gererEmployer(Model model ,
			@RequestParam(name="motCle" , defaultValue="" )String mc){
		
		List<User> users = ur.chercher("%"+mc+"%");
		model.addAttribute("users",users);
		return "gestionEmploye";
	}
	
	@RequestMapping(value="/formAdmin",method=RequestMethod.GET)
	public String formAdmin(Model model ,
			@RequestParam(name="motCle" , defaultValue="" )String mc){
		
		List<User> users = ur.chercher("%"+mc+"%");
		model.addAttribute("users",users);
		model.addAttribute("user",new User());
		
		return "formAdmin";
	}
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String edit(Model model , @RequestParam(value="userId")int id){
		User  u = ur.findById(id);
		
		model.addAttribute("u",u);
		
		return "EditForm";
	}
	@RequestMapping(value="/SaveEmploye",method=RequestMethod.POST)
	public String saveEmploye(@Valid User empl , BindingResult br, Model model){
	
		
		//formulaire
		if ( br.hasErrors()){
		return "formAdmin";
	}
		empl.setRole("Employe");
		empl.setActive(true);
		empl.setExpirer(false);
		empl.setDateDebut(new Date());
		empl.setAbonement("illimite");
		ur.save(empl);
		
		return "redirect:/Admin/gestionEmploye";
	}
	
	@Transactional
	@RequestMapping(value="/DeleteEmploye", method=RequestMethod.GET)
	public String deleteEmploye(@RequestParam(value="userId") int id){
	
		ur.deleteById(id);
		
		return "redirect:/Admin/gestionEmploye";
	}	
}
