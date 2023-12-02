package project1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import project1.model.Book;
import project1.model.PhysicalBook;
import project1.service.book.BookService;
import project1.view.CustomerView;
import static project1.database.Constants.Tables.*;

import java.util.List;

public class CustomerController {
    private final CustomerView customerView;
    private static BookService bookService;
    private ViewPhysicalBooksButtonListener phListener;
    private String bookType;

    public CustomerController(CustomerView customerView, BookService bookService) {
        this.customerView = customerView;
        this.bookService = bookService;

        this.phListener = new ViewPhysicalBooksButtonListener();

        this.customerView.addViewBooksButtonListener(new ViewBooksButtonListener());
        this.customerView.addViewPhysicalBooksButtonListener(phListener);
        this.customerView.addViewEBooksButtonListener(new ViewEBooksButtonListener());
        this.customerView.addViewAudioBooksButtonListener(new ViewAudioBooksButtonListener());
        this.customerView.addBuyBooksButtonListener(new BuyBookButtonListener());
        this.customerView.addLogOutButtonListener(new LogOutButtonListener());
    }

    private class ViewBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = BOOK;
            List<Book> books = bookService.findAll(BOOK);
            customerView.setBooks(books, BOOK);
        }
    }

    private class ViewAudioBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = AUDIO_BOOK;
            List<Book> books = bookService.findAll(AUDIO_BOOK);
            customerView.setBooks(books, AUDIO_BOOK);
        }
    }

    private class ViewPhysicalBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = PHYSICAL_BOOK;
            List<Book> books = bookService.findAll(PHYSICAL_BOOK);
            customerView.setBooks(books, PHYSICAL_BOOK);
        }
    }

    private class ViewEBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = EBOOK;
            List<Book> books = bookService.findAll(EBOOK);
            customerView.setBooks(books, EBOOK);
        }
    }

    private class BuyBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book selectedBook = customerView.getSelectedItem();
            if (selectedBook == null) {
                customerView.setActionTargetText("", Color.BLUE);
                return;
            }
            if (selectedBook instanceof PhysicalBook) {
                boolean stock = bookService.updateStock((PhysicalBook) selectedBook);
                if (stock) {
                    customerView.setActionTargetText("Stock available", Color.GREEN);
                    phListener.handle(new ActionEvent());
                }
                else {
                    customerView.setActionTargetText("Out of stock", Color.FIREBRICK);
                }
            }
            else {
                customerView.setActionTargetText("Stock available", Color.GREEN);
            }
        }
    }
}
