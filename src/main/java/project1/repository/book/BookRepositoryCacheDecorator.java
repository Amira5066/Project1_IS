package project1.repository.book;

import project1.model.Book;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator {
    private Cache<Book> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache) {
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public boolean update(Book newBook, Book oldBook) {
        return false;
    }

    @Override
    public List<Book> findAll(String bookType) {
        if (cache.hasResult()) {
            return cache.load();
        }

        List<Book> books = decoratedRepository.findAll(bookType);
        cache.save(books);

        return books;
    }

    @Override
    public Optional<Book> findById(Long id, String bookType) {

        if (cache.hasResult()) {
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedRepository.findById(id, bookType);
    }

    @Override
    public boolean save(Book book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public boolean delete(Book book, String table) {
        return false;
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
