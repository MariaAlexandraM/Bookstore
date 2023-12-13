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

public class AdminController {

    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final BookService bookService;
    private final UserService userService;


    public AdminController(AdminView adminView, AuthenticationService authenticationService, BookService bookService, UserService userService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.bookService = bookService;
        this.userService = userService;

        this.adminView.addBooksButtonListener(new BooksButtonListener());
        this.adminView.addUsersButtonListener(new UsersButtonListener());
    }

    private class BooksButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            CRUDBooksView CRUDbooksView = new CRUDBooksView(bookService);
            CRUDBooksController CRUDbooksController = new CRUDBooksController(CRUDbooksView, bookService);
        }
    }

    private class UsersButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            CRUDUsersView CRUDusersView = new CRUDUsersView(userService);
            CRUDUsersController CRUDusersController = new CRUDUsersController(CRUDusersView, userService);
        }
    }

}
