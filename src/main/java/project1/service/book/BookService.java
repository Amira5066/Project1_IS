package project1.service.book;

import project1.model.Book;
import project1.model.PhysicalBook;

import java.util.List;

public interface BookService {
    List<Book> findAll(String bookType);
    Book findById(Long id, String bookType);
    boolean save(Book book);
    boolean updateStock(PhysicalBook book);
    int getAgeOfBook(Long id);
}
