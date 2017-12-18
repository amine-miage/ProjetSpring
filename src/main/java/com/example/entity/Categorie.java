package com.example.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class Categorie implements Serializable {

    @Id @GeneratedValue
    private int id;
    private String name;

    @OneToMany(mappedBy = "categorie")
    private List<Media> medias;

    public Categorie() { }

    public Categorie(String name) { this.name = name; }

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

    @JsonIgnore
    public List<Media> getMedias() {
        return medias;
    }
    @JsonSetter
    public void setFilms(List<Media> medias) {
        this.medias = medias;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

