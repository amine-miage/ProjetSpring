package com.example.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.example.metier.DemandeMetier;

@Entity
@DiscriminatorValue("DM")
public class DemandeDeleteMedia extends Demande{

    @OneToOne
    @JoinColumn(name = "Media_id")
    private Media media;

    public DemandeDeleteMedia() {  }
    public DemandeDeleteMedia(int id, String status,String genre, Media media) {
        super(id, status, genre);
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }
    public void setMedia(Media media) {
        this.media = media;
    }


    @Override
    public String toString() {
        return "DemandeDeleteMedia{" +super.toString()+", "+
                ", film=" + media +
                '}';
    }
	@Override
	public void accepted(DemandeMetier dm) {
		dm.deleteMedia(this.getMedia());
	}
}

