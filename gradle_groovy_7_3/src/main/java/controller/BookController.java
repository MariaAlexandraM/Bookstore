package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;
import service.book.BookService;

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public ObservableList<Book> getAllBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        books.addAll(bookService.findAll());
        return books;
    }
}
