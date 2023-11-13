package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();
    Optional<Book> findById(Long id);
    boolean save(Book book);
    // int getAgeOfBook(Long id); // nu mai am asta aici pt ca trb calculata, deci ii in business logic, nu o preluam. in repo is doar functiile ce manipuleaza valori si date
    void removeAll();
}
