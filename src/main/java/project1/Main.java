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


        Book book = new BookBuilder()
                .setTitle("Harry Potter")
                .setAuthor("J.K. Rowling")
                .setPublishedDate(LocalDate.of(2010, 7, 3))
                .build();


        System.out.println(book);

        bookService.save(book);

        System.out.println(bookService.findAll());

        System.out.println(bookService.findAll());
        System.out.println(bookService.findAll());
    }
}