package project1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import project1.model.AudioBook;
import project1.model.Book;
import project1.model.EBook;
import project1.model.PhysicalBook;
import project1.model.builder.AudioBookBuilder;
import project1.model.builder.BookBuilder;
import project1.model.builder.EBookBuilder;
import project1.model.builder.PhysicalBookBuilder;
import project1.service.book.BookService;
import project1.service.user.EmployeeService;
import project1.view.EmployeeView;

import static project1.database.Constants.Tables.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    private final EmployeeView employeeView;
    private static BookService bookService;
    private static EmployeeService employeeService;
    private ViewPhysicalBooksButtonListener phListener;
    private ViewBooksButtonListener bookListener;
    private ViewEBooksButtonListener ebookListener;
    private ViewAudioBooksButtonListener audioBookListener;
    private String bookType;
    private final int TEXT_ID = 0;
    private final int TEXT_TITLE = 1;
    private final int TEXT_AUTHOR = 2;
    private final int TEXT_PUBLISHED_DATE = 3;
    private final int TEXT_PRICE = 4;
    private final int TEXT_COVER = 5;
    private final int TEXT_STOCK = 6;
    private final int TEXT_FORMAT = 7;
    private final int TEXT_RUNTIME = 8;
    private final int[] TEXT_FIELDS = {TEXT_TITLE, TEXT_AUTHOR, TEXT_PUBLISHED_DATE, TEXT_PRICE, TEXT_COVER, TEXT_STOCK, TEXT_FORMAT, TEXT_RUNTIME};
    private static List<TextField> textFields = new ArrayList<>();
    private static List<TableColumn> tableColumns = new ArrayList<>();
    public EmployeeController(EmployeeView employeeView, BookService bookService, EmployeeService employeeService) {
        this.employeeView = employeeView;
        this.bookService = bookService;
        this.employeeService = employeeService;

        this.phListener = new ViewPhysicalBooksButtonListener();
        this.bookListener = new ViewBooksButtonListener();
        this.audioBookListener = new ViewAudioBooksButtonListener();
        this.ebookListener = new ViewEBooksButtonListener();

        this.employeeView.addViewBooksButtonListener(bookListener);
        this.employeeView.addViewPhysicalBooksButtonListener(phListener);
        this.employeeView.addViewEBooksButtonListener(ebookListener);
        this.employeeView.addViewAudioBooksButtonListener(audioBookListener);
        this.employeeView.addSellBooksButtonListener(new SellBookButtonListener());
        this.employeeView.addAddBooksButtonListener(new AddBooksButtonListener());
        this.employeeView.addDeleteBooksButtonListener(new DeleteBookButtonListener());
        this.employeeView.addEditBooksButtonListener(new EditBookButtonListener());
        this.employeeView.addCreateReportButtonListener(new CreateReportButtonListener());
        this.employeeView.addLogOutButtonListener(new LogOutButtonListener());

        this.textFields = employeeView.getTextFields();

        this.tableColumns = employeeView.getTableColumns();
    }

    private void updateTableView() {
        switch (bookType) {
            case BOOK:
                bookListener.handle(new ActionEvent());
                break;
            case PHYSICAL_BOOK:
                phListener.handle(new ActionEvent());
                break;
            case EBOOK:
                ebookListener.handle(new ActionEvent());
                break;
            case AUDIO_BOOK:
                audioBookListener.handle(new ActionEvent());
                break;
        }
    }

    private class ViewBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = BOOK;
            List<Book> books = bookService.findAll(BOOK);
            employeeView.setBooks(books, BOOK);

            textFields.get(TEXT_RUNTIME).setVisible(false);
            textFields.get(TEXT_STOCK).setVisible(false);
            textFields.get(TEXT_COVER).setVisible(false);
            textFields.get(TEXT_FORMAT).setVisible(false);

            textFields.get(TEXT_STOCK).setManaged(false);
            textFields.get(TEXT_COVER).setManaged(false);
            textFields.get(TEXT_FORMAT).setManaged(false);
            textFields.get(TEXT_RUNTIME).setManaged(false);
        }
    }

    private class ViewAudioBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = AUDIO_BOOK;
            List<Book> books = bookService.findAll(AUDIO_BOOK);
            employeeView.setBooks(books, AUDIO_BOOK);

            textFields.get(TEXT_RUNTIME).setVisible(true);

            textFields.get(TEXT_RUNTIME).setManaged(true);

            textFields.get(TEXT_STOCK).setVisible(false);
            textFields.get(TEXT_COVER).setVisible(false);
            textFields.get(TEXT_FORMAT).setVisible(false);

            textFields.get(TEXT_STOCK).setManaged(false);
            textFields.get(TEXT_COVER).setManaged(false);
            textFields.get(TEXT_FORMAT).setManaged(false);
        }
    }

    private class ViewPhysicalBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = PHYSICAL_BOOK;
            List<Book> books = bookService.findAll(PHYSICAL_BOOK);
            employeeView.setBooks(books, PHYSICAL_BOOK);

            textFields.get(TEXT_STOCK).setVisible(true);
            textFields.get(TEXT_COVER).setVisible(true);

            textFields.get(TEXT_STOCK).setManaged(true);
            textFields.get(TEXT_COVER).setManaged(true);

            textFields.get(TEXT_RUNTIME).setVisible(false);
            textFields.get(TEXT_FORMAT).setVisible(false);

            textFields.get(TEXT_RUNTIME).setManaged(false);
            textFields.get(TEXT_FORMAT).setManaged(false);
        }
    }

    private class ViewEBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = EBOOK;
            List<Book> books = bookService.findAll(EBOOK);
            employeeView.setBooks(books, EBOOK);
            textFields.get(TEXT_FORMAT).setVisible(true);

            textFields.get(TEXT_FORMAT).setManaged(true);

            textFields.get(TEXT_RUNTIME).setVisible(false);
            textFields.get(TEXT_STOCK).setVisible(false);
            textFields.get(TEXT_COVER).setVisible(false);

            textFields.get(TEXT_STOCK).setManaged(false);
            textFields.get(TEXT_COVER).setManaged(false);
            textFields.get(TEXT_RUNTIME).setManaged(false);
        }
    }

    private class SellBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book selectedBook = employeeView.getSelectedItem();
            if (selectedBook == null) {
                employeeView.setActionTargetText("", Color.BLUE);
                return;
            }
            if (selectedBook instanceof PhysicalBook) {
                boolean stock = bookService.updateStock((PhysicalBook) selectedBook);
                if (stock) {
                    employeeView.setActionTargetText("Stock available", Color.GREEN);
                    updateTableView();
                    employeeService.updateSales(LoginController.getId(), selectedBook.getPrice());
                }
                else {
                    employeeView.setActionTargetText("Out of stock", Color.FIREBRICK);
                }
            }
            else {
                employeeService.updateSales(LoginController.getId(), selectedBook.getPrice());
                employeeView.setActionTargetText("Stock available", Color.GREEN);
            }
        }
    }

    private class DeleteBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book selectedBook = employeeView.getSelectedItem();
            if (selectedBook == null) {
                return;
            }
            bookService.delete(selectedBook, bookType);
        }
    }

    private class CreateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            employeeService.createReport(LoginController.getId());
        }
    }

    private Book createBook(String bookType, Book oldBook) {
        Book book = null;
        String id = textFields.get(TEXT_ID).getText();
        String title = textFields.get(TEXT_TITLE).getText();
        String author = textFields.get(TEXT_AUTHOR).getText();
        String date = textFields.get(TEXT_PUBLISHED_DATE).getText();
        String price = textFields.get(TEXT_PRICE).getText();

        switch (bookType) {
            case BOOK:
                book = new BookBuilder(Book.class)
                        .setId(oldBook == null? null : (id.isEmpty()? oldBook.getId() : Integer.parseInt(id)))
                        .setTitle(title.isEmpty()? oldBook.getTitle() : title)
                        .setAuthor(author.isEmpty()? oldBook.getAuthor() : author)
                        .setPublishedDate(date.isEmpty()? oldBook.getPublishedDate() : LocalDate.parse(date))
                        .setPrice(price.isEmpty()? oldBook.getPrice() : Integer.parseInt(price))
                        .build();
                break;
            case PHYSICAL_BOOK:
                String cover = textFields.get(TEXT_COVER).getText();
                String stock = textFields.get(TEXT_STOCK).getText();
                book = new PhysicalBookBuilder(PhysicalBook.class)
                        .setCover(cover.isEmpty()? ((PhysicalBook) oldBook).getCover() : cover)
                        .setStock(stock.isEmpty()? ((PhysicalBook) oldBook).getStock() : Integer.parseInt(stock))
                        .setId(oldBook == null? null : (id.isEmpty()? oldBook.getId() : Integer.parseInt(id)))
                        .setTitle(title.isEmpty()? oldBook.getTitle() : title)
                        .setAuthor(author.isEmpty()? oldBook.getAuthor() : author)
                        .setPublishedDate(date.isEmpty()? oldBook.getPublishedDate() : LocalDate.parse(date))
                        .setPrice(price.isEmpty()? oldBook.getPrice() : Integer.parseInt(price))
                        .build();
                break;
            case EBOOK:
                String format = textFields.get(TEXT_FORMAT).getText();
                book = new EBookBuilder(EBook.class)
                        .setFormat(format.isEmpty()? ((EBook) oldBook).getFormat() : format)
                        .setId(oldBook == null? null : (id.isEmpty()? oldBook.getId() : Integer.parseInt(id)))
                        .setTitle(title.isEmpty()? oldBook.getTitle() : title)
                        .setAuthor(author.isEmpty()? oldBook.getAuthor() : author)
                        .setPublishedDate(date.isEmpty()? oldBook.getPublishedDate() : LocalDate.parse(date))
                        .setPrice(price.isEmpty()? oldBook.getPrice() : Integer.parseInt(price))
                        .build();
                break;
            case AUDIO_BOOK:
                String runtime = textFields.get(TEXT_RUNTIME).getText();
                book = new AudioBookBuilder(AudioBook.class)
                        .setRunTime(runtime.isEmpty()? ((AudioBook) oldBook).getRunTime() : Integer.parseInt(runtime))
                        .setId(oldBook == null? null : (id.isEmpty()? oldBook.getId() : Integer.parseInt(id)))
                        .setTitle(title.isEmpty()? oldBook.getTitle() : title)
                        .setAuthor(author.isEmpty()? oldBook.getAuthor() : author)
                        .setPublishedDate(date.isEmpty()? oldBook.getPublishedDate() : LocalDate.parse(date))
                        .setPrice(price.isEmpty()? oldBook.getPrice() : Integer.parseInt(price))
                        .build();
                break;
        }
        return book;
    }

    private class AddBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book book = createBook(bookType, null);
            bookService.save(book);
            employeeView.clearTextFields();
        }
    }

    private class EditBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book selectedBook = employeeView.getSelectedItem();
            if (selectedBook == null) {
                return;
            }
            Book newBook = createBook(bookType, selectedBook);
            bookService.update(newBook, selectedBook);
            employeeView.clearTextFields();
            updateTableView();
        }
    }
}
