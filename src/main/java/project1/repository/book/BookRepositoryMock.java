package project1.repository.book;

import project1.model.Book;
import project1.model.PhysicalBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {
    private List<Book> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll(String bookType) {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id, String bookType) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean updateStock(PhysicalBook book) {
        return false;
    }
}
