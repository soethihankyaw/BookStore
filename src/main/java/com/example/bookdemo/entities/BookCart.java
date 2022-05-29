package com.example.bookdemo.entities;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class BookCart {

    private List<BookDto>books =
            new ArrayList<>();

    public void addBook(Book book) {
        books.add(bookToDTO(book));
    }

    private BookDto bookToDTO(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPrice(),
                book.getImageUrl(),
                book.getPublishTime());

    }

    public void removeBook(Book book) {
        books.remove(bookToDTO(book));
    }
    public void clearCart() {
        books.clear();
    }

    public List<BookDto>getBooks() {
        return books;
    }

    public int getCartSize() {
        return books.size();
    }
}
