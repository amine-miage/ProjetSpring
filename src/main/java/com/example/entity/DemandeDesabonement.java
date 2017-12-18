package com.example.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.example.metier.DemandeMetier;

@Entity
@DiscriminatorValue("DD")
public class DemandeDesabonement extends Demande{
   
    private String raison;

    public DemandeDesabonement(int id, String status,String genre, String raison) {
        super(id, status,genre);
        this.raison = raison;
    }
    public DemandeDesabonement() { }
    @Override
    public String toString() {
        return "DemandeDesabonnement{" +super.toString()+", "+
                ", raison='" + raison + '\'' +
                '}';
    }

    public String getRaison() {
        return raison;
    }
    public void setRaison(String raison) {
        this.raison = raison;
    }
	@Override
	public void accepted(DemandeMetier dm) {
		dm.Desabonement(super.getUser());
	}
}
