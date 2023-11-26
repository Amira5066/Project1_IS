package project1.repository.book;

import project1.model.AudioBook;
import project1.model.Book;
import project1.model.EBook;
import project1.model.PhysicalBook;
import project1.model.builder.AudioBookBuilder;
import project1.model.builder.BookBuilder;
import project1.model.builder.EBookBuilder;
import project1.model.builder.PhysicalBookBuilder;
import static project1.database.Constants.Tables.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {
    private Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    private Book getBookFromResultSet(ResultSet resultSet, Class<? extends Book> myClass, ResultSet additionalResultSet) throws SQLException {
        Book book = null;
        if (myClass == PhysicalBook.class) {
            book = new PhysicalBookBuilder((Class<PhysicalBook>) myClass)
                    .setCover(additionalResultSet.getString("cover"))
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .build();
        }
        if (myClass == EBook.class) {
            book = new EBookBuilder((Class<EBook>) myClass)
                    .setFormat(additionalResultSet.getString("format"))
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .build();
        }
        else if (myClass == AudioBook.class) {
            book = new AudioBookBuilder((Class< AudioBook>) myClass)
                    .setRunTime(additionalResultSet.getInt("runTime"))
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .build();
        }
        else {
            book = new BookBuilder<>((Class< Book>) myClass)
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .build();
        }
        return book;
    }

    private ResultSet findFieldsOfBook(Long id, String table) {
        String sql = "SELECT * FROM "+ table + " WHERE id = " + id + " ;";
        ResultSet resultSet = null;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet.next()? resultSet : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book buildBookFromResultSets(ResultSet resultSet) {
        Book book = null;
        ResultSet additionalResultSet = null;
        try {
            if ((additionalResultSet = findFieldsOfBook(resultSet.getLong("id"),EBOOK)) != null) {
                book = getBookFromResultSet(resultSet, EBook.class, additionalResultSet);
            } else if ((additionalResultSet = findFieldsOfBook(resultSet.getLong("id"), AUDIO_BOOK)) != null) {
                book = getBookFromResultSet(resultSet, AudioBook.class, additionalResultSet);
            } else if  ((additionalResultSet = findFieldsOfBook(resultSet.getLong("id"), PHYSICAL_BOOK)) != null) {
                book = getBookFromResultSet(resultSet, PhysicalBook.class, additionalResultSet);
            } else {
                book = getBookFromResultSet(resultSet, Book.class, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    private Book getBookFromResultSet(ResultSet resultSet, Class<? extends Book> myClass) throws SQLException {
        Book book = null;
        if (myClass == PhysicalBook.class) {
            book = new PhysicalBookBuilder((Class<PhysicalBook>) myClass)
                    .setCover(resultSet.getString("cover"))
                    .setStock(resultSet.getInt("stock"))
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .setPrice(resultSet.getInt("Price"))
                    .build();
        }
        else if (myClass == EBook.class) {
            book = new EBookBuilder((Class<EBook>) myClass)
                    .setFormat(resultSet.getString("format"))
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .setPrice(resultSet.getInt("Price"))
                    .build();
        }
        else if (myClass == AudioBook.class) {
            book = new AudioBookBuilder((Class< AudioBook>) myClass)
                    .setRunTime(resultSet.getInt("runTime"))
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .setPrice(resultSet.getInt("Price"))
                    .build();
        }
        else {
            book = new BookBuilder<>((Class< Book>) myClass)
                    .setId(resultSet.getLong("id"))
                    .setTitle(resultSet.getString("title"))
                    .setAuthor(resultSet.getString("author"))
                    .setPublishedDate(new Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                    .setPrice(resultSet.getInt("Price"))
                    .build();
        }
        return book;
    }

    private Book buildBookFromResultSets(ResultSet resultSet, String bookType) {
        Book book = null;
        try {
            switch (bookType) {
                case PHYSICAL_BOOK:
                    book = getBookFromResultSet(resultSet, PhysicalBook.class);
                    break;
                case AUDIO_BOOK:
                    book = getBookFromResultSet(resultSet, AudioBook.class);
                    break;
                case EBOOK:
                    book = getBookFromResultSet(resultSet, EBook.class);
                    break;
                default: book = getBookFromResultSet(resultSet, Book.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> findAll(String bookType) {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book;";
        switch (bookType) {
            case PHYSICAL_BOOK: sql = "SELECT book.id as id, author, title, publishedDate, physical_book.cover as cover, price, physical_book.stock as stock " +
                                        "FROM book JOIN physical_book ON book.id = physical_book.id;";
                                break;
            case AUDIO_BOOK: sql = "SELECT book.id as id, author, title, publishedDate, audio_book.runTime as runTime, price " +
                                   "FROM book JOIN audio_book ON book.id = audio_book.id;";
                             break;
            case EBOOK: sql = "SELECT book.id as id, author, title, publishedDate, ebook.format as format, price " +
                    "FROM book JOIN ebook ON book.id = ebook.id;";
                break;
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                books.add(buildBookFromResultSets(resultSet, bookType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public boolean updateStock(PhysicalBook book) {
        String sql = "UPDATE physical_book SET stock = stock - 1 WHERE id = ? AND cover = ? AND stock > 0;";
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, book.getId());
            statement.setString(2, book.getCover());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
    }

    @Override
    public Optional<Book> findById(Long id, String bookType) {
        String sql = "SELECT * FROM book WHERE id = ?;";

        switch (bookType) {
            case PHYSICAL_BOOK:
                sql = "SELECT book.id, title, author, publishedDate, price, physical_book.stock as stock " +
                      "FROM book JOIN physical_book ON book.id = physical_book.id " +
                      "WHERE book.id = ?;";
                break;
            case AUDIO_BOOK:
                sql = "SELECT book.id, title, author, publishedDate, price, audio_book.runTime as runTime " +
                      "FROM book JOIN audio_book ON book.id = audio_book.id " +
                      "WHERE book.id = ?;";
                break;
            case EBOOK:
                sql = "SELECT book.id, title, author, publishedDate, price, ebook.format as format " +
                      "FROM book JOIN ebook ON book.id = ebook.id " +
                      "WHERE book.id = ?;";
                break;
        }
        Book book = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            book = resultSet.next() ? buildBookFromResultSets(resultSet, bookType) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public boolean save(Book book) {
        String sql = "INSERT INTO book (author, title, publishedDate) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));

            preparedStatement.executeUpdate();
            if (book instanceof AudioBook) {
                sql = "INSERT INTO audio_book (id, runTime) VALUES (LAST_INSERT_ID(), ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, ((AudioBook) book).getRunTime());
                preparedStatement.executeUpdate();
            }
            else if (book instanceof EBook) {
                sql = "INSERT INTO ebook (id, format) VALUES (LAST_INSERT_ID(), ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ((EBook) book).getFormat());
                preparedStatement.executeUpdate();
            }
            else if (book instanceof PhysicalBook) {
                sql = "INSERT INTO physical_book (id, cover) VALUES (LAST_INSERT_ID(), ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, ((PhysicalBook) book).getCover());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String sql = "SET FOREIGN_KEY_CHECKS = 0;";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            sql = "TRUNCATE TABLE audio_book;";
            statement.execute(sql);
            sql = "TRUNCATE TABLE ebook;";
            statement.execute(sql);
            sql = "TRUNCATE TABLE book;";
            statement.execute(sql);
            sql = "SET FOREIGN_KEY_CHECKS = 1;";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
