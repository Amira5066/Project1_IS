package repository;

import org.junit.jupiter.api.BeforeAll;
import project1.database.DatabaseConnectionFactory;
import project1.model.Book;
import project1.model.builder.BookBuilder;
import project1.repository.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookRepositoryMySQLTest {
    private static BookRepository bookRepository;
    private static Book book1;
    private static Book book2;

    @BeforeAll
    public static void init() {
        bookRepository = new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection());
        book1 = new BookBuilder()
                .setId(1L)
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();
        book2 = new BookBuilder()
                .setId(2L)
                .setTitle("some book")
                .setAuthor("some author")
                .setPublishedDate(LocalDate.of(2015, 3, 7))
                .build();
    }
    @org.junit.jupiter.api.Test
    void save() {
        bookRepository.removeAll();
        assertEquals(true, bookRepository.save(book1));
        assertEquals(true, bookRepository.save(book2));
    }

    @org.junit.jupiter.api.Test
    void findById() {
        assertEquals(Optional.empty(), bookRepository.findById(500L));
        assertEquals(Optional.of(book1).get().toString(), bookRepository.findById(1L).get().toString());
        assertEquals(Optional.of(book2).get().toString(), bookRepository.findById(2L).get().toString());
    }
}

