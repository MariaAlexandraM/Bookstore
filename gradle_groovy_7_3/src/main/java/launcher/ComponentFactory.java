package launcher;

import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import model.User;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImplementation;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImplementation;
import service.user.UserService;
import service.user.UserServiceImplementation;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthenticationService authenticationService;
    private final BookService bookService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepository bookRepository; // !!!!! trb schimbat in interfata (1:52:40) // TODO Done
    private static volatile ComponentFactory instance; // tp tema 2

    public static ComponentFactory getInstance(Boolean componentsForTests, Stage stage) { // trb sa il protejam altfel, singleton u asta, sa fie thread safe
        if (instance == null) {
            synchronized (ComponentFactory.class) {
                if (instance == null) {
                    instance = new ComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }

    public ComponentFactory(Boolean componentsForTests, Stage stage) { // singleton
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceImplementation(userRepository, rightsRolesRepository);
        this.loginView = new LoginView(stage);
        this.bookRepository = new BookRepositoryMySQL(connection); // trb un book service, nu am voie sa apelez repo-u direct // TODO
        this.bookService = new BookServiceImplementation(bookRepository);
        this.userService = new UserServiceImplementation(userRepository);
        this.loginController = new LoginController(loginView, authenticationService, bookService, userService);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public LoginController getLoginController() {
        return loginController;
    }

}
