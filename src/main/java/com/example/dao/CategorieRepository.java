package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
		public Categorie findByName(String name);
		public Categorie findById(int id);
}