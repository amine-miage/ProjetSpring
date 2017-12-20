package com.example.entity;



import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Entity
public class User implements Serializable {

    @Id @GeneratedValue
    private int id;
    
    @Size(min=2, max=20)
    private String name;
    
    @Size(min=2)
    private String prenom;
    @NotEmpty
    @Email
    private String mail;
    @NotEmpty
    @Size(min=5 , message ="erreur, 5 caractères minimum ")
    private String password;
    private Boolean active;
    private String role;
    private String abonement;
    private boolean expirer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Demande> demandes;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Media> Medias;
    
    public User() { 
    	
    }
    public User(String name, String prenom, String mail) {
        this.name = name;
        this.prenom = prenom;
        this.mail = mail;
        this.active=true;
    }
    public User(String name, String prenom, String mail, String password) {
        this.name = name;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.active=true;
    }
    public User(String name, String prenom, String mail, String password, String abonement) {
    	this.name = name;
    	this.prenom = prenom;
    	this.mail = mail;
    	this.password = password;
    	this.abonement = abonement;
    	this.active=true;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    
   public String getPassword() {
        return password;
    }
   public void setPassword(String password) {
        this.password = password;
    }
   
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
	
	
	@JsonSerialize(using=GetDemandes.class)
    public Collection<Demande> getDemandes() {
		return demandes;
	}
	
	
	@JsonSerialize(using=GetMedias.class)
    public Collection<Media> getMedias() {
		return Medias;
	}
	
	public void setMedias(Collection<Media> Medias) {
		this.Medias = Medias;
	}
	public void setDemandes(Collection<Demande> demandes) {
		this.demandes = demandes;
	}
	
	
	public String getAbonement() {
		return abonement;
	}
	public void setAbonement(String abonement) {
		this.abonement = abonement;
	}
	public boolean isExpirer() {
		LocalDateTime dt = new LocalDateTime(this.getDateDebut());
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime dp = null;
		
		if(abonement.equals("annuel")) {
			dp = dt.plusMinutes(50);
		}
		else if(abonement.equals("mensuel")){
			dp = dt.plusMinutes(50);
		}
		else if(abonement.equals("illimite")) {
			dp = dt.plusYears(100);
		}
		
//		System.out.println("["+name+"] : dp - dt - today  : "+dp+", "+dt+" ,"+today);
//		System.out.println("["+name+"] : "+today.isAfter(dp));
//		
		return today.isAfter(dp);
	}
	public void setExpirer(boolean expirer) {
		this.expirer = expirer;
	}
	
	
	@Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prenom='" + prenom + '\'' +
                ", e-mail=" + mail +
                ", active=" + active +
                '}';
    }


}


@Component
class GetDemandes extends JsonSerializer<List<Demande>> {
    @Override
    public void serialize(List<Demande> demandes, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {

        gen.writeStartArray();
        for (Demande demande : demandes) {
            gen.writeStartObject();
	            gen.writeNumberField("id",demande.getId());
	            gen.writeStringField("status",demande.getStatus());
	            gen.writeStringField("genre",demande.getGenre());
            gen.writeEndObject();
        }
        gen.writeEndArray();

    }
}

@Component
class GetMedias extends JsonSerializer<List<Media>> {
    @Override
    public void serialize(List<Media> Medias, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {

        gen.writeStartArray();
        for (Media media : Medias) {
            gen.writeStartObject();
	            gen.writeNumberField("id",media.getId());
	            gen.writeStringField("nom",media.getName());
	            gen.writeStringField("Description",media.getDescription());
	            gen.writeStringField("Type",media.getType());
	            gen.writeNumberField("size", media.getSize());
	            
	            
            gen.writeEndObject();
        }
        gen.writeEndArray();

    }
}
