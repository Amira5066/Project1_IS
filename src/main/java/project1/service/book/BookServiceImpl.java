package project1.service.book;

import project1.model.Book;
import project1.model.PhysicalBook;
import project1.repository.book.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements  BookService {

    final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll(String bookType) {
        return bookRepository.findAll(bookType);
    }

    @Override
    public Book findById(Long id, String bookType) {
        return bookRepository.findById(id, bookType).orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book, String table) {
        return bookRepository.delete(book, table);
    }

    @Override
    public boolean updateStock(PhysicalBook book) {
        return bookRepository.updateStock(book);
    }

    @Override
    public boolean update(Book newBook, Book oldBook) {
        return bookRepository.update(newBook, oldBook);
    }
}
