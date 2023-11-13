import database.JDBConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import repository.book.*;
import service.BookService;
import service.BookServiceImplementation;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("hellow");
        //  mysql -u root -p

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



    }
}
