package project1.repository.book;

import project1.model.Book;
import project1.model.PhysicalBook;

public abstract class BookRepositoryDecorator implements BookRepository {
    protected BookRepository decoratedRepository;

    public BookRepositoryDecorator(BookRepository bookRepository) {
        this.decoratedRepository = bookRepository;
    }

    @Override
    public boolean updateStock(PhysicalBook book) {
        return false;
    }

    @Override
    public boolean update(Book newBook, Book oldBook) {
        return false;
    }
}
