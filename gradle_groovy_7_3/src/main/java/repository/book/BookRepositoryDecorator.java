package repository.book;

public abstract class BookRepositoryDecorator implements BookRepository{

    protected BookRepository decoratedRepository; // tot un book repo dar cu o functionalitate adaugata

    public BookRepositoryDecorator(BookRepository bookRepository) {
        this.decoratedRepository = bookRepository;
    }
}
