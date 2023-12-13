package repository.book;

import model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator{

    private Cache<Book> cache;
    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache) {
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<Book> findAll() {
        if(cache.hasResult()) {
            return cache.load(); // ia Din cache, asta face load
        }

        List<Book> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return decoratedRepository.findById(id); // posibil sa fie gresit // TODO
    }

    @Override
    public boolean save(Book book) {
        // se modfica bd deci cahce-u nu mai ii valid
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();

    }

    @Override
    public boolean updateBook(Book book) {
        return false;
    }

    @Override
    public void decreaseQty(Book book, int quantity) {

    }
}
