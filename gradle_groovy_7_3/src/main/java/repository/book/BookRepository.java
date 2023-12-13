package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();
    Optional<Book> findById(Long id);
    boolean save(Book book);
    void removeAll();
    boolean updateBook(Book book);
    void decreaseQty(Book book, int quantity);
}
