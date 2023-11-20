import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import model.validator.UserValidator;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImplementation;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;


import java.sql.Connection;
import java.time.LocalDate;

import static database.Constants.Schemas.PRODUCTION;

//public class Main extends Application {
//    public static void main(String[] args){
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
//
//        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
//        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
//
//        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
//                rightsRolesRepository);
//
//        final LoginView loginView = new LoginView(primaryStage);
//
//        final UserValidator userValidator = new UserValidator(userRepository);
//
//        new LoginController(loginView, authenticationService, userValidator);
//    }
//}



public class Main {
    public static void main(String[] args) {
        System.out.println("mysql -u root -p; daca nu merge, activeaza din Services");

        JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
        //connectionWrapper.getConnection();


        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(connectionWrapper.getConnection()),
                new Cache<>()) ;

        BookService bookService = new BookServiceImplementation(bookRepository);

        Book book = new BookBuilder()
                .setTitle("Fahrenheit 451")
                .setAuthor("Ray Bradbury")
                .setPublishedDate(LocalDate.of(2010, 6, 2))
                .build();
        System.out.println(book);

        bookRepository.save(book);
        System.out.println(bookRepository.findAll());

       // JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper("test_library");
       // connectionWrapper.getConnection();

        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();

        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);

        authenticationService.register("maria", "a1.b2.c3");

        System.out.println(authenticationService.login("maria", "a1.b2.c3"));
    }
}



