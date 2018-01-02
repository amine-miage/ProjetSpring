package com.example.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Categorie;
import com.example.entity.Media;
//import com.example.entity.Tag;
import com.example.entity.User;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer>{

    public Set<Media> findByName(String name);
    Media findById(int idMedia);
    public Set<Media> findByUser(User u);
    void deleteById(int idMedia);
   // public Set<Media> findByTagsName(String name);
    //public Set<Media> findByTagsNameIn(Collection<String> names);
 //   public Set<Media> findByTagsIn(Collection<Tag> tags);
    
    @Query("select m from Media m where m.name like :x ")
    List<Media> chercherMedia(@Param("x") String mc);
    
    public Set<Media> findByCategorieId(int id);
    public Set<Media> findByCategorieName(String name);
    public Set<Media> findByCategorieNameIn(Collection<String> name);
    public Set<Media> findByCategorie(Categorie cat);
    public Set<Media> findByCategorieIn(Collection<Categorie> cat);
    
    
}
