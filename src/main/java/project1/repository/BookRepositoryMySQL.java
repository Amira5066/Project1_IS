package project1.repository;

import project1.model.AudioBook;
import project1.model.Book;
import project1.model.EBook;
import project1.model.builder.AudioBookBuilder;
import project1.model.builder.BookBuilder;
import project1.model.builder.EBookBuilder;

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
            if ((additionalResultSet = findFieldsOfBook(resultSet.getLong("id"), "ebook")) != null) {
                book = getBookFromResultSet(resultSet, EBook.class, additionalResultSet);
            } else if ((additionalResultSet = findFieldsOfBook(resultSet.getLong("id"), "audio_book")) != null) {
                book = getBookFromResultSet(resultSet, AudioBook.class, additionalResultSet);
            } else {
                book = getBookFromResultSet(resultSet, Book.class, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM book;";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

                books.add(buildBookFromResultSets(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?;";
        Book book = null;
        Optional<Book> optionalBook = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            book = resultSet.next() ? buildBookFromResultSets(resultSet) : null;
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
