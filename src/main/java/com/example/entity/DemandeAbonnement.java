package com.example.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.example.metier.DemandeMetier;

@Entity
@DiscriminatorValue("AB")
public class DemandeAbonnement extends Demande{
	
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1231020826229438201L;

	
	public DemandeAbonnement() {};
	public DemandeAbonnement(int id, String status, String genre, User user) {
		super(id, status, genre, user);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	public String toString() {
		return "DemandeAbonnement ["+super.toString()+"]";
	}
	
	
}
