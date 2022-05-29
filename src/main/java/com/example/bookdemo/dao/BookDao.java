package com.example.bookdemo.dao;

import com.example.bookdemo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

    Book findBookByTitle(String title);
}
