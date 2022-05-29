package com.example.bookdemo.service;

import com.example.bookdemo.dao.BookDao;
import com.example.bookdemo.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Transactional
    public Book findBookById(int bookId) {
       return bookDao.getById(bookId);
    }

    @Transactional
    public List<Book> findAllBooks() {
       return bookDao.findAll();
    }

    @Transactional
    public void saveBook(Book book) {
        bookDao.save(book);
    }

    @Transactional
    public void removeBook(int bookId) {
        Book book = findBookById(bookId);
        bookDao.delete(book);
    }

    @Transactional
    public Book searchBook(String title) {
        return bookDao.findBookByTitle(title);
    }

    @Transactional
    public Book updateBook(int bookId,Book newBook) {
        Book oldBook = findBookById(bookId);

        oldBook.setPrice(newBook.getPrice());
        oldBook.setTitle(newBook.getTitle());
        oldBook.setImageUrl(newBook.getImageUrl());
        oldBook.setPublishTime(newBook.getPublishTime());

        return oldBook;
    }
}
