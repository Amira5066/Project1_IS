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
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id, null);

        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }

    @Override
    public boolean updateStock(PhysicalBook book) {
        return bookRepository.updateStock(book);
    }
}
