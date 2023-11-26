package project1.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import project1.model.AudioBook;
import project1.model.Book;
import project1.model.EBook;
import project1.model.PhysicalBook;
import project1.service.book.BookService;
import project1.view.ClientView;
import static project1.database.Constants.Tables.*;

import java.util.List;

public class ClientController {
    private final ClientView clientView;
    private static BookService bookService;
    private ViewPhysicalBooksButtonListener phListener;
    private String bookType;

    public ClientController(ClientView clientView, BookService bookService) {
        this.clientView = clientView;
        this.bookService = bookService;

        this.phListener = new ViewPhysicalBooksButtonListener();

        this.clientView.addViewBooksButtonListener(new ViewBooksButtonListener());
        this.clientView.addViewPhysicalBooksButtonListener(phListener);
        this.clientView.addViewEBooksButtonListener(new ViewEBooksButtonListener());
        this.clientView.addViewAudioBooksButtonListener(new ViewAudioBooksButtonListener());
        this.clientView.addBuyBooksButtonListener(new BuyBookButtonListener());
    }

    private class ViewBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = BOOK;
            List<Book> books = bookService.findAll(BOOK);
            clientView.setBooks(books, BOOK);
        }
    }

    private class ViewAudioBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = AUDIO_BOOK;
            List<Book> books = bookService.findAll(AUDIO_BOOK);
            clientView.setBooks(books, AUDIO_BOOK);
        }
    }

    private class ViewPhysicalBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = PHYSICAL_BOOK;
            List<Book> books = bookService.findAll(PHYSICAL_BOOK);
            clientView.setBooks(books, PHYSICAL_BOOK);
        }
    }

    private class ViewEBooksButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            bookType = EBOOK;
            List<Book> books = bookService.findAll(EBOOK);
            clientView.setBooks(books, EBOOK);
        }
    }

    private class BuyBookButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Book selectedBook = clientView.getSelectedItem();
            if (selectedBook == null) {
                clientView.setActionTargetText("", Color.BLUE);
                return;
            }
            if (selectedBook instanceof PhysicalBook) {
                boolean stock = bookService.updateStock((PhysicalBook) selectedBook);
                if (stock) {
                    clientView.setActionTargetText("Stock available", Color.GREEN);
                    phListener.handle(new ActionEvent());
                }
                else {
                    clientView.setActionTargetText("Out of stock", Color.FIREBRICK);
                }
            }
            else {
                clientView.setActionTargetText("Stock available", Color.GREEN);
            }
        }
    }
}
