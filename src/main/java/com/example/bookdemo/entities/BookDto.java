package com.example.bookdemo.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BookDto {

    private Integer id;
    private String title;
    private String price;
    private String imageUrl;
    private String publishTime;

    public BookDto() {
    }

    public BookDto(Integer id, String title, String price, String imageUrl, String publishTime) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.publishTime = publishTime;
    }
}
