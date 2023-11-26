package project1.model.builder;

import project1.model.Book;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

public class BookBuilder<T extends Book> {
    private T book;

    public BookBuilder(Class<T> bookClass) {
        try {
            Constructor<T> constructor = bookClass.getDeclaredConstructor();
            this.book = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of Book.", e);
        }
    }

    public BookBuilder<T> setId(Long id) {
        book.setId(id);
        return this;
    }
    public BookBuilder<T> setAuthor(String author) {
        book.setAuthor(author);
        return this;
    }

    public BookBuilder<T> setTitle(String title) {
        book.setTitle(title);
        return this;
    }

    public BookBuilder<T> setPublishedDate(LocalDate publishedDate) {
        book.setPublishedDate(publishedDate);
        return this;
    }

    public BookBuilder<T> setPrice(int price) {
        book.setPrice(price);
        return this;
    }
    public T build() {
        return book;
    }
}

