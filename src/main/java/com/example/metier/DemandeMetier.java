package com.example.metier;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dao.DemandeRepository;

import com.example.dao.MediaRepository;
import com.example.dao.UserRepository;
import com.example.entity.Demande;
import com.example.entity.Media;
import com.example.entity.User;


@Component 
public class DemandeMetier{
	@Autowired
	MediaRepository dr;
	@Autowired
	UserRepository ur;
	@Autowired
	DemandeRepository dmr;
	
	Demande demande;
	
	
	public Demande getDemande() {
		return demande;
	}
	public void setDemande(Demande demande) {
		this.demande = demande;
	}

	public void inscription(User u){
		dmr.delete(this.getDemande());
		User user = ur.findById(u.getId());
		user.setActive(true);
		user.setDateDebut(new Date());
		ur.save(user);
	}
	
	public void abonnement(User u) {
		dmr.delete(this.getDemande());
		User user = ur.findById(u.getId());
		user.setActive(true);
		ur.save(user);
	}
	
	
	public void Emprunt(User u) {
		
	}
	public void deleteMedia(Media M){
		dmr.delete(this.getDemande());
		dr.delete(M);	
	}
	
	
}