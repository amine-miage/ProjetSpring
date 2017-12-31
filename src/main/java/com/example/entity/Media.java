package com.example.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Media implements Serializable{

    @Id
    @GeneratedValue
    private int id;
    private String name;
    
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Basic(fetch = FetchType.LAZY)
    @Column(name="description", length=10000)
    private String description;
 
    private String type;
    private long size;
    
    private String photo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(orphanRemoval=true,mappedBy = "media") 
    private Set<Tag> tags;

    

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    public Media() { }

    public Media(String name, String description, String type, long size) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.size = size;
        this.date = new Date();
        
    }

    public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Tag> getTags() {
        return tags;
    }
    public void setTags(Set<Tag> cles) {
    	if(this.tags == null) this.tags = cles;
    	else{
    		this.tags.clear();
            this.tags.addAll(cles);
    	}
    }

    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @JsonSerialize(using = GetMediaUser.class)
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", name=" + name + ", description="
				+ description + ", type=" + type + ", size=" + size
				+ ", date=" + date + "]";
	}

	

}

@Component
class GetMediaUser extends JsonSerializer<User> {
    
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



