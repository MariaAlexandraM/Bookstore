package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.User;
import service.book.BookService;
import service.user.AuthenticationService;
import service.user.UserService;
import service.user.UserServiceImplementation;
import view.*;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final BookService bookService;


    public EmployeeController(EmployeeView employeeView, BookService bookService) {
        this.employeeView = employeeView;
        this.bookService = bookService;

        this.employeeView.addBooksButtonListener(new BooksButtonListener());
    }

    private class BooksButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            CRUDBooksView CRUDbooksView = new CRUDBooksView(bookService);
            CRUDBooksController CRUDbooksController = new CRUDBooksController(CRUDbooksView, bookService);
        }
    }

}
