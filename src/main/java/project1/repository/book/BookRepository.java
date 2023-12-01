package project1.repository.book;

import project1.model.Book;
import project1.model.PhysicalBook;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll(String bookType);
    Optional<Book> findById(Long id, String bookType);
    boolean save(Book book);
    void removeAll();
    boolean updateStock(PhysicalBook book);
    boolean update(Book newBook, Book oldBook);
    boolean delete(Book book, String table);
}
