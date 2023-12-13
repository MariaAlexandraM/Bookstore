package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.User;
import service.book.BookService;
import service.user.AuthenticationService;
import service.user.UserService;
import service.user.UserServiceImplementation;
import view.AdminView;
import view.BooksView;
import view.LoginView;
import view.UsersView;

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
            BooksView booksView = new BooksView(bookService);
            BooksController booksController = new BooksController(booksView, bookService);
        }
    }

    private class UsersButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            UsersView usersView = new UsersView(userService);
            UsersController usersController = new UsersController(usersView, userService);
        }
    }

}
