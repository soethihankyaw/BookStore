package com.example.bookdemo.dao;

import com.example.bookdemo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersDao extends JpaRepository<Users, Integer> {

    public Optional<Users> findByEmail(String email);
}
