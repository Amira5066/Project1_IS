package project1.service.book;

import project1.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    boolean save(Book book);
    int getAgeOfBook(Long id);
}
