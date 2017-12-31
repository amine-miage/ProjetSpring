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
		private Date dateEmprunt;
		private int IdMedia;

	    public DemandeEmprunt(int id, String status, String genre, Date dateEmprunt,int IdMedia) {
	      //  super(id, status,genre);
	        this.dateEmprunt = dateEmprunt;
	        this.IdMedia=IdMedia;
	    }

		@Override
		public void accepted(DemandeMetier dm) {
			dm.Emprunt(super.getUser(),IdMedia);
			
		}
		
		
		
		
		@Override
		public String toString() {
			return "DemandeInscription ["+super.toString()+" ,raison=" + dateEmprunt + "]";
		}
		
		
	}

