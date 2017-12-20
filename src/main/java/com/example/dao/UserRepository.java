package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);
    List<User> findByMail(String mail);
    User findById(int id);
    User findByMailAndPassword(String mail, String password);
    List<User> findByRole(String r);
    @Query("delete from User u where id = :id ")
    void deleteUserById(@Param("id") int id);

}
