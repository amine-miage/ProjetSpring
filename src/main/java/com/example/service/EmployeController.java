
package com.example.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.DemandeRepository;
import com.example.dao.MediaRepository;
import com.example.dao.UserRepository;
import com.example.entity.Demande;
import com.example.entity.DemandeEmprunt;
import com.example.entity.Media;
import com.example.entity.User;

@Controller

public class EmployeController{

	
	@Autowired
	UserRepository ur;
	@Autowired
	MediaRepository mr;
	@Autowired
	DemandeRepository dr;
	
	
	@Value("${dir.images}")
	private String imageDir;
	
	@RequestMapping(value="/gestionClient",method=RequestMethod.GET)
	public String gererClient(Model model ,
			@RequestParam(name="motCle" , defaultValue="" )String mc){
		
		List<User> users = ur.chercherClient("%"+mc+"%");
		model.addAttribute("users",users);
		return "gestionClient";
	}
	
	@RequestMapping(value="/gestionDemande",method=RequestMethod.GET)
	public String gererDemande(Model model ){
		
		List<Demande> demandes = dr.findAll() ;
		
		model.addAttribute("demandes",demandes);
		
		return "gestionDemande";
	}
	
	@RequestMapping(value="/gestionMedia",method=RequestMethod.GET)
	public String gererMedia(Model model, @RequestParam(name="motCle" , defaultValue="" )String mc){
		
		//List<Media> medias = mr.findAll();
		List<Media> medias = mr.chercherMedia("%"+mc+"%");
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
	public String editMedia(@Valid Media media , BindingResult br, Model model , @RequestParam(name="motCle" , defaultValue="" )String mc,@RequestParam(name="picture") MultipartFile file) throws IllegalStateException, IOException{
		if ( br.hasErrors()){
			
			
			return "EditFormMedia";
	}
		
		mr.save(media);
		if(!(file.isEmpty())){
			media.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+media.getId()));
		}
		//formulaire
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
	
		s.setRole("CLIENT");
		
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
	@RequestMapping(value = "/acceptDemande" , method= RequestMethod.GET)
    public String accepteDemande(@RequestParam(value="userID") int id, @RequestParam(value="dmdID") int idDmd) {
		
		User user = ur.findById(id);	
		dr.delete(idDmd);
		user.setActive(true);
		user.setAbonement("abonné");
		ur.save(user);
		
		return "redirect:/gestionDemande"; 
	}
	
	@RequestMapping(value = "/acceptEmprunt" , method= RequestMethod.GET)
    public String acceptEmprunt( @RequestParam(value="dmdID") int idDmd,@RequestParam(value="userID") int id,@RequestParam(value="idMedia") int mediaId) {
		
		User user = ur.findById(id);	
		Media media =mr.findById(mediaId);
		media.setUser(user);
		dr.delete(idDmd);
	   
		
		return "redirect:/gestionDemande"; 
	}
	@RequestMapping(value = "/refusDemande" , method= RequestMethod.GET)
    public String refusDemande(@RequestParam(value="dmdID") int idDmd) {
		
			
		dr.delete(idDmd);
		
		
		return "redirect:/gestionDemande"; 
	}
	@RequestMapping(value = "/DemandeEmprunt", method = RequestMethod.GET)
    public String dmdEprunt(@RequestParam(value="idMedia") int mediaId,@RequestParam(value="idEmprunt") int userId) {	
		User u = ur.findById(userId);
		Media m = mr.findById(mediaId);
		DemandeEmprunt demande = new DemandeEmprunt(1,"waiting","demande d`emprunt de "+" "+u.getMail(),u,m);
		demande.setCat("Emprunt");
		dr.save(demande);
		return "redirect:/ConsulterEmprunt";
		
		
	}
	@RequestMapping(value = "/ConsulterEmprunt" , method= RequestMethod.GET)
    public String affichageEmprunt
		
		(HttpServletRequest httpServletRequest, Model model) throws Exception {
			List<Media> medias = mr.findAll();
			model.addAttribute("medias",medias);
			
			//model.addAttribute("users",users);
			HttpSession httpSession = httpServletRequest.getSession();
			
			SecurityContext securityContext=(SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
			
			String username = securityContext.getAuthentication().getName();
			
			User u =ur.findByMail(username);
			//int iduser=u.getId();
			
			Set<Media> liste = mr.findByUser(u);
			model.addAttribute("u",u);
			model.addAttribute("liste",liste);
		
		
		
		return "consulterEmprunt"; 
	}
	@RequestMapping(value = "/EmployePage" , method= RequestMethod.GET)
    public String employePag(){
		return "EmployePage";
	}
	
	@RequestMapping(value = "/Abonner" , method= RequestMethod.GET)
    public String Abonner(){
		return "Abonner";
	}
}
