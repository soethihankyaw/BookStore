package com.example.bookdemo.dao;

import com.example.bookdemo.entities.UserBook;
import com.example.bookdemo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersBookDao extends JpaRepository<UserBook, Integer> {

    @Query("select b from UserBook b where b.users.email = :email")
    UserBook findUserBookByUserId(@Param("email") String email);
}
