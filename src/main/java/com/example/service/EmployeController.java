
package com.example.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.MediaRepository;
import com.example.dao.UserRepository;
import com.example.entity.Media;
import com.example.entity.User;

@Controller

public class EmployeController{

	
	@Autowired
	UserRepository ur;
	@Autowired
	MediaRepository mr;
	
	@Value("${dir.images}")
	private String imageDir;
	
	@RequestMapping(value="/gestionClient",method=RequestMethod.GET)
	public String gererClient(Model model ,
			@RequestParam(name="motCle" , defaultValue="" )String mc){
		
		List<User> users = ur.chercherClient("%"+mc+"%");
		model.addAttribute("users",users);
		return "gestionClient";
	}
	
	@RequestMapping(value="/gestionMedia",method=RequestMethod.GET)
	public String gererMedia(Model model ){
		
		List<Media> medias = mr.findAll();
		model.addAttribute("medias",medias);
		return "gestionMedia";
	}
	
	@RequestMapping(value="/formMedia",method=RequestMethod.GET)
	public String formMedia(Model model ){
		
		
		
		model.addAttribute("media",new Media());
		
		return "formMedia";
	}
	
	@RequestMapping(value="/SaveMedia",method=RequestMethod.POST)
	public String saveMedia(@Valid Media media , BindingResult br, Model model , @RequestParam(name="motCle" , defaultValue="" )String mc,@RequestParam(name="picture") MultipartFile file) throws IllegalStateException, IOException{
		if ( br.hasErrors()){
			List<Media> medias = mr.findAll();
			model.addAttribute("medias",medias);
			
			return "formMedia";
	}
		media.setTags(null);
		mr.save(media);
		if(!(file.isEmpty())){
			media.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+media.getId()));
		}
		//formulaire
		
		
		
		return "redirect:/gestionMedia";
	}

	
	@RequestMapping(value="/getPhoto",produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws FileNotFoundException, IOException{
		File f = new File(imageDir+id);
	    return IOUtils.toByteArray(new FileInputStream(f));	
	}
	
	@RequestMapping(value="/editerMedia",method=RequestMethod.GET)
	public String editMedia(Model model , @RequestParam(value="mediaId")int id){
		Media m = mr.findById(id);
		
		model.addAttribute("m",m);
		
		return "EditFormMedia";
	}
	
	@RequestMapping(value = "/EditMedia", method = RequestMethod.POST)
	public String SaveMedia(Media m , BindingResult br )
	{
	
		
		mr.save(m);
		return "redirect:/gestionMedia";
	}
	
	@Transactional
	@RequestMapping(value="/DeleteMedia", method=RequestMethod.GET)
	public String deleteMedia(@RequestParam(value="mediaId") int id){
	
		mr.deleteById(id);
		
		return "redirect:/gestionMedia";
	}	
	
	@RequestMapping(value="/editerClient",method=RequestMethod.GET)
	public String edit(Model model , @RequestParam(value="userId")int id){
		User  u = ur.findById(id);
		
		model.addAttribute("u",u);
		
		return "EditFormClient";
	}
	
	@RequestMapping(value = "/EditClient", method = RequestMethod.POST)
	public String SaveUser(User s , BindingResult br )
	{
	
		s.setRole("client");
		//s.setActive(false);
		s.setExpirer(false);
		ur.save(s);
		return "redirect:/gestionClient";
	}
	@RequestMapping(value="/abonnerClient",method=RequestMethod.GET)
	public String abonnement(Model model , @RequestParam(value="userId")int id){
		User  u = ur.findById(id);
		
		model.addAttribute("u",u);
		
		return "abonnement";
	}
	@RequestMapping(value = "/abonnementClient", method = RequestMethod.POST)
	public String abonnementUser(User s , BindingResult br )
	{
	
		
		//s.setActive(false);
		
		ur.updateByAbonnement(s.getAbonement());
		return "redirect:/gestionClient";
	}
	@Transactional
	@RequestMapping(value="/DeleteClient", method=RequestMethod.GET)
	public String deleteClient(@RequestParam(value="userId") int id){
	
		ur.deleteById(id);
		
		return "redirect:/gestionClient";
	}	
}
