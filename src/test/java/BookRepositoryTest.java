import project1.database.DatabaseConnectionFactory;
import project1.model.Book;
import project1.model.builder.BookBuilder;
import project1.repository.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookRepositoryTest {
    @org.junit.jupiter.api.Test
    void save() {
        BookRepository bookRepository = new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection());
        bookRepository.removeAll();
        Book book1 = new BookBuilder()
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();
        Book book2 = new BookBuilder()
                .setTitle("some book")
                .setAuthor("some author")
                .setPublishedDate(LocalDate.of(2015, 3, 7))
                .build();
        assertEquals(true, bookRepository.save(book1));
        assertEquals(true, bookRepository.save(book2));
    }

    @org.junit.jupiter.api.Test
    void findById() {
        BookRepository bookRepository = new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection());
        Book book = new BookBuilder()
                .setTitle("Harry Potter")
                .setId(1L)
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();
        Book book2 = new BookBuilder()
                .setTitle("some book")
                .setAuthor("some author")
                .setPublishedDate(LocalDate.of(2015, 3, 7))
                .build();
        assertEquals(Optional.empty(), bookRepository.findById(500L));
        assertEquals(Optional.of(book), bookRepository.findById(1L));
    }
}

