package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import service.book.BookService;
import service.user.AuthenticationService;
import view.BooksView;
import view.CustomerView;

public class CustomerController {
    private final CustomerView customerView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;


    public CustomerController(CustomerView customerView, AuthenticationService authenticationService, BookService bookService) {
        this.customerView = customerView;
        this.authenticationService = authenticationService;
        this.bookService = bookService;

        this.customerView.addBooksButtonListener(new BooksButtonListener());
    }

    private class BooksButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            BooksView booksView = new BooksView(bookService);
            BooksController booksController = new BooksController(booksView, bookService);
        }
    }
}
