package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {

    private final List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }


    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst(); // putea, si () -> ().getId. () ii o functie fara nume, it ii nume
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean updateBook(Book book) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public String decreaseQty(Book book, int quantity) {
        return "";
    }
}
