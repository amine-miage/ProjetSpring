package com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.CategorieRepository;
import com.example.dao.MediaRepository;
import com.example.dao.UserRepository;
import com.example.entity.Categorie;

@RestController
public class CategorieService {

    @Autowired
    CategorieRepository cr;
    @Autowired
    MediaRepository dr;
    @Autowired
    UserRepository ur;

    @RequestMapping(value = "/categorie", method = RequestMethod.GET)
    public Collection<Categorie> categories(){
        return cr.findAll();
    }

    @RequestMapping(value = "/categorie/{id}", method = RequestMethod.GET)
    public Categorie categorieById(@PathVariable int id){
        return cr.findOne(id);
    }

    @RequestMapping(value = "/categorie/{id}", method = RequestMethod.DELETE)
    public void deleteCategorieById(@PathVariable int id){
        cr.delete(id);
    }

    @RequestMapping(value = "/categorie", method = RequestMethod.POST)
    public Categorie addCategorie(@RequestBody Categorie categorie){
        return cr.save(categorie);
    }

    @RequestMapping(value = "/categorie/media/{id}")
    public int listMediaInCategories(@PathVariable int id){
        return dr.findByCategorieId(id).size();
    }
}
