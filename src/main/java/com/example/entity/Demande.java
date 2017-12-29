package com.example.entity;




import com.example.metier.DemandeMetier;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.io.IOException;
import java.io.Serializable;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=DemandeDeleteMedia.class, name = "EM"),
        @JsonSubTypes.Type(value=DemandeAbonnement.class, name = "AB"),
})

public abstract class Demande implements Serializable{

	@Id @GeneratedValue
    private int id;
    private String status;
    private String genre;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

	public Demande() { super(); }

	public Demande(int id, String status,String genre) {
		super();
		this.id = id;
		this.status = status;
        this.genre = genre;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    @JsonSerialize(using = GetDemandeUser.class)
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public abstract void accepted(DemandeMetier dm);

    @Override
	public String toString() {
		return "Demande [id=" + id + ", genre ="+genre+" ,status=" + status + ", user= "+user.toString()+"]";
	}

}




@Component
class GetDemandeUser extends JsonSerializer<User> {
    
	@Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {


        gen.writeStartObject();
            gen.writeNumberField("id",user.getId());
            gen.writeStringField("name",user.getName());
            gen.writeStringField("prenom",user.getPrenom());
            gen.writeStringField("mail",user.getMail());
            gen.writeStringField("abonement",user.getAbonement());
            gen.writeBooleanField("active",user.getActive());
            gen.writeObjectField("role",user.getRole());
        gen.writeEndObject();


    }
}
