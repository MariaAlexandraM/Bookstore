package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Role;
import model.User;
import model.validator.Notification;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImplementation;
import service.user.AuthenticationService;
import view.*;

import java.sql.Connection;
import java.util.List;

import static database.Constants.Roles.*;


// controleaza abs toata aplicatia
// specific mvc, pt fiecare view sa am controller dedicat
public class LoginController {

    private final LoginView loginView;

    // mereu service uri, nu repo! its all abt layers bby. ca shrek.
    private final AuthenticationService authenticationService;
    private final BookService bookService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, BookService bookService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.bookService = bookService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    // TODO
    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Login successful!");
                // TODO
                // acuma aici vreau sa deschid a new window cu functionalitati in functie de user role

                User user = loginNotification.getResult(); // returneaza useru daca s-o autentificat cu succes, which it did
                List<Role> roles = user.getRoles(); // ia rolurile user-ului respectiv
                String role = roles.get(0).getRole(); // pot avea mai multe, de ex si employee si customer
                switch(role) {
                    case ADMINISTRATOR:
                        System.out.println("Admin logged in!");
                        AdminView adminView = new AdminView();
                        AdminController adminController = new AdminController(adminView, authenticationService);

                        //BooksView booksView = new BooksView(bookService);
                      //  BooksController booksController = new BooksController(booksView, bookService);
                        break;

                    case EMPLOYEE:
                        System.out.println("Employee logged in!");
                        EmployeeView employeeView = new EmployeeView();
                        EmployeeController employeeController = new EmployeeController(employeeView, authenticationService);
                        break;

                    case CUSTOMER:
                        System.out.println("Customer logged in!");
                        CustomerView customerView = new CustomerView();
                        CustomerController customerController = new CustomerController(customerView, authenticationService);

                        BooksView booksView = new BooksView(bookService);
                        BooksController booksController = new BooksController(booksView, bookService);
                        break;
                }


                //adminView.show();
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!\nYou need to log in to continue.");
            }
        }
    }
}

