package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Java Bean.
 * POJO - Plain Old Java Object
 * */

public class Book {
    private Long id;
    private String author;
    private String title;
    private LocalDate publishedDate;
    private int stock;
    private float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Book author: %s | title: %s | published date: %s | stock: %s | price: %s", author, title, publishedDate, stock, price);
    }
}
