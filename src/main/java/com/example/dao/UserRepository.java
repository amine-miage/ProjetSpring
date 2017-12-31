package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);
    List<User> findByMail(String mail);
    User findById(int id);
    User findByMailAndPassword(String mail, String password);
    List<User> findByRole(String r);
    
    void deleteById( int id);
    @Query("select u from User u where u.name like :x AND u.role='Employe'")
    List<User> chercherEmploye(@Param("x") String mc);
    
    @Query("select u from User u where u.name like :x AND u.role='Client'")
    List<User> chercherClient(@Param("x") String mc);
    
    @Query("select u from User u where u.abonement='NULL' AND u.role='Client'")
    List<User> chercherClientNonAbo();
    
    @Transactional
    @Modifying
    @Query("update User u set u.abonement = :x")
    void updateByAbonnement(@Param("x") String abonnement);

}
