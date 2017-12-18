package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByName(String name);
    public Role findById(int id);
}
