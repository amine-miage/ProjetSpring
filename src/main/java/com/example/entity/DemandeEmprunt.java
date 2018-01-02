package com.example.entity;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.example.metier.DemandeMetier;


@Entity
@DiscriminatorValue("EM")
public class DemandeEmprunt extends Demande{


		
		/**
	 * 
	 */
	private static final long serialVersionUID = -1523222928485311053L;
		

		public DemandeEmprunt() {};
	    public DemandeEmprunt(int id, String status, String genre,User user,Media media) {
	       super(id, status,genre,user,media);
	    }
		
		
		
		
		@Override
		public String toString() {
			return "DemandeInscription ["+super.toString()+" ,raison="   ;
		}
		
		
	}

