package controller;

import service.book.BookService;
import view.BooksView;

public class BooksController {
    private final BooksView booksView;
    private final BookService bookService;


    public BooksController(BooksView booksView, BookService bookService) {
        this.booksView = booksView;
        this.bookService = bookService;
    }
}
