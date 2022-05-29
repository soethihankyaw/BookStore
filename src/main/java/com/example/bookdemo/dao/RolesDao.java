package com.example.bookdemo.dao;

import com.example.bookdemo.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesDao extends JpaRepository<Roles, Integer> {
}
