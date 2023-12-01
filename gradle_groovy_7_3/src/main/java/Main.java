import controller.LoginController;
import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Book;
import model.builder.BookBuilder;
import model.validator.UserValidator;
import repository.book.*;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImplementation;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImplementation;
import view.LoginView;

import java.sql.Connection;
import java.time.LocalDate;

import static database.Constants.Schemas.PRODUCTION;

public class Main {// extends Application {
    public static void main(String[] args) {
        System.out.println("hellow");
        //  mysql -u root -p

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        //connectionWrapper.getConnection();


        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(connectionWrapper.getConnection()),
                new Cache<>());

        BookService bookService = new BookServiceImplementation(bookRepository);

//      Book book = new BookBuilder()
//              .setTitle("Fahrenheit 451")
//              .setAuthor("Ray Bradbury")
//              .setPublishedDate(LocalDate.of(2010, 6, 2))
//              .build();
//      System.out.println(book);
//
//      bookRepository.save(book);
//      System.out.println(bookRepository.findAll());
//
        // JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        // connectionWrapper.getConnection();


        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();
        // !!!!!!!!!!!!!!!!!!!
        // folosim ca tip al obiectului interfata, nu implementarea in sine
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        if (userRepository.existsByUsername("maria")) {
            System.out.println("username taken");
        } else {
            AuthenticationService authenticationService = new AuthenticationServiceImplementation(userRepository, rightsRolesRepository);
            authenticationService.register("maria", "Parola1234!");

            //System.out.println(authenticationService.login("maria", "Parola1234!"));

        }
    }
}
