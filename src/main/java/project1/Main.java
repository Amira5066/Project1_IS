package project1;

import project1.model.*;
import project1.model.builder.*;
import project1.repository.*;
import project1.database.*;
import project1.service.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");


        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
                new Cache<>()
        );

        BookService bookService = new BookServiceImpl(bookRepository);


        // order of the setters matters!!!!
        Book book = new AudioBookBuilder(AudioBook.class)
                .setRunTime(13)
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();
        bookService.save(book);

        book = new BookBuilder(Book.class)
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();
        bookService.save(book);


        book = new EBookBuilder(EBook.class)
                .setFormat("pdf")
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();

        bookService.save(book);

        System.out.println(bookService.findAll());
        System.out.println(bookService.findById(300L));
        System.out.println(bookService.getAgeOfBook(3L));

    }
}