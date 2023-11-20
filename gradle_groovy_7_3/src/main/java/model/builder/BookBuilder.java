package model.builder;

import model.Book;

import java.time.LocalDate;
import java.util.Date;

// folosim builder uri pt ca ajuta cand am mai mult de 3-4 parametri

public class BookBuilder {
    private Book book;
    public BookBuilder() {
        book = new Book(); // design pattern creational care se ocupa cu crearea obiectelor deci pot face asta
    }

    public BookBuilder setId(Long id) {
        book.setId(id);
        return this;
    }

    public BookBuilder setAuthor(String author) {
        book.setAuthor(author);
        return this;
    }

    public BookBuilder setTitle(String title) {
        book.setTitle(title);
        return this;
    }

    public BookBuilder setPublishedDate(LocalDate publishedDate) {
        book.setPublishedDate(publishedDate);
        return this;
    }

    public Book build() {
        return book;
    }
}
