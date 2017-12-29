package com.example.entity;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.example.metier.DemandeMetier;


@Entity
@DiscriminatorValue("DI")
public class DemandeEmprunt extends Demande{


		
		private Date dateEmprunt;

	    public DemandeEmprunt(int id, String status, String genre, Date dateEmprunt) {
	        super(id, status,genre);
	        this.dateEmprunt = dateEmprunt;
	    }

		@Override
		public void accepted(DemandeMetier dm) {
			dm.Emprunt(super.getUser());
			
		}
		
		
		
		
		@Override
		public String toString() {
			return "DemandeInscription ["+super.toString()+" ,raison=" + dateEmprunt + "]";
		}
		
		
	}

