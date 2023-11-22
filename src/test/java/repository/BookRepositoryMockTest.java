package repository;

import org.junit.jupiter.api.*;
import project1.model.Book;
import project1.model.builder.BookBuilder;
import project1.repository.book.BookRepository;
import project1.repository.book.BookRepositoryCacheDecorator;
import project1.repository.book.BookRepositoryMock;
import project1.repository.book.Cache;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMockTest {
    private static BookRepository bookRepository;

    @BeforeAll
    public static void setupClass(){
        bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMock(),
                new Cache<>()
        );
    }

    @Test
    public void findAll(){
        assertEquals(0, bookRepository.findAll().size());
    }

    @Test
    public void findById(){
        final Optional<Book> books = bookRepository.findById(1L);
        assertTrue(books.isEmpty());
    }

    @Test
    public void save(){
        Book book = new BookBuilder<>(Book.class)
                .setAuthor("Author")
                .setTitle("Title")
                .build();

        assertTrue(bookRepository.save(book));
    }

}
