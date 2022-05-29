package com.example.bookdemo.service;

import com.example.bookdemo.entities.Book;
import com.example.bookdemo.entities.BookCart;
import com.example.bookdemo.entities.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private BookCart bookCart;

    public int cartSize = 0;

    public void addToCart(Book book) {
        bookCart.addBook(book);
        updateCartSize();
    }

    public List<BookDto>allBooksInCart() {
        return bookCart.getBooks();
    }

    public void clearCart() {
        bookCart.clearCart();
        updateCartSize();
    }

    public void removeBookCart(Book book) {
        bookCart.removeBook(book);
        updateCartSize();
    }

    private void updateCartSize() {
        cartSize = bookCart.getBooks().size();
    }

}
