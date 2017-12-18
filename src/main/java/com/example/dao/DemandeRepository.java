package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {

    @Query("select d.genre from Demande d where d.id = ?1")
    public String findDemandeById(int id);
}
