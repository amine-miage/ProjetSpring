package com.example.metier;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.UserRepository;
import com.example.entity.User;

@Repository
public class UserMetier {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    public List<User> listUser(){
        return userRepository.findAll();
    }

    public User updateUser(int id, User user){
        User u =  userRepository.findById(id);
        if(u!=null) {
            user.setId(id);
            return userRepository.save(user);
        }else return new User();
    }

    public User getUser(int id){
        return userRepository.findById(id);
    }

    public void deleteUser(int id){
        userRepository.delete(id);
    }

    public User saveUser(User u) {
        return userRepository.save(u);
    }

    public User findUser(String mail, String password){
        if(mail != null && password!= null) return userRepository.findByMailAndPassword(mail,password);
        else return null;
    }
}

