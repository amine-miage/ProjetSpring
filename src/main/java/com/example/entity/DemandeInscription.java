package com.example.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.example.metier.DemandeMetier;

@Entity
@DiscriminatorValue("DI")
public class DemandeInscription extends Demande{
	
	private String raison;

    public DemandeInscription(){}
    public DemandeInscription(int id, String status, String genre, String raison) {
        super(id, status,genre);
        this.raison = raison;
    }

	@Override
	public void accepted(DemandeMetier dm) {
		dm.inscription(super.getUser());
	}
	
	@Override
	public String toString() {
		return "DemandeInscription ["+super.toString()+" ,raison=" + raison + "]";
	}
	
	
}
