package project1.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project1.model.AudioBook;
import project1.model.Book;
import project1.model.EBook;
import project1.model.PhysicalBook;

import java.time.LocalDate;
import java.util.List;

import static project1.database.Constants.Tables.*;

public class CustomerView {
    private TableView table;
    private TableColumn idCol;
    private TableColumn titleCol;
    private TableColumn authorCol;
    private TableColumn publishedDateCol;
    private TableColumn coverCol;
    private TableColumn formatCol;
    private TableColumn runtimeCol;
    private TableColumn stockCol;
    private TableColumn priceCol;
    private HBox hb;
    private Button viewBooksButton;
    private Button viewAudioBooksButton;
    private Button viewEBooksButton;
    private Button viewPhysicalBooksButton;
    private Button buyBookButton;
    private Text actionTarget;

    private ObservableList<Book> books;
    private String bookType;


    public CustomerView(Stage window) {
        window.setTitle("Book Store");
        window.setWidth(900);
        window.setHeight(600);

        VBox vbox = new VBox();
        initializeVBox(vbox);

        Scene scene = new Scene(new Group());

        initializeSceneTitle(vbox);

        initializeFields(vbox);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        window.setScene(scene);
        window.show();
    }

    private void initializeVBox(VBox vbox){
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
    }

    private void initializeSceneTitle(VBox vbox){
        Text sceneTitle = new Text("Hello Customer!");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        vbox.getChildren().add(sceneTitle);
    }

    private void initializeFields(VBox vbox){
        bookType = BOOK;
        hb = new HBox();

        table = new TableView<>();
        table.setEditable(false);

        idCol = new TableColumn("Id");
        idCol.setMaxWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));

        titleCol = new TableColumn("Title");
        titleCol.setMinWidth(200);
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        authorCol = new TableColumn("Author");
        authorCol.setMinWidth(200);
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

        publishedDateCol = new TableColumn("Published date");
        publishedDateCol.setMinWidth(200);
        publishedDateCol.setCellValueFactory(new PropertyValueFactory<Book, LocalDate>("publishedDate"));

        coverCol = new TableColumn("Cover Type");
        coverCol.setMinWidth(100);
        coverCol.setCellValueFactory(new PropertyValueFactory<PhysicalBook, String>("cover"));

        runtimeCol = new TableColumn("Runtime");
        runtimeCol.setMinWidth(60);
        runtimeCol.setCellValueFactory(new PropertyValueFactory<AudioBook, Integer>("runTime"));

        formatCol = new TableColumn("Format");
        formatCol.setMinWidth(60);
        formatCol.setCellValueFactory(new PropertyValueFactory<EBook, String>("format"));

        stockCol = new TableColumn("Stock");
        stockCol.setMaxWidth(60);
        stockCol.setCellValueFactory(new PropertyValueFactory<PhysicalBook, Integer>("stock"));

        priceCol = new TableColumn("Price");
        priceCol.setMaxWidth(60);
        priceCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("price"));

        viewBooksButton = new Button("View books");
        viewAudioBooksButton = new Button("View audio books");
        viewEBooksButton = new Button("View ebooks");
        viewPhysicalBooksButton = new Button("View physical books");
        buyBookButton = new Button("Buy Book");

        actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);

        hb.getChildren().addAll(viewBooksButton, viewPhysicalBooksButton, viewAudioBooksButton, viewEBooksButton, buyBookButton, actionTarget);
        hb.setSpacing(3);

        table.getColumns().addAll(idCol, titleCol, authorCol, publishedDateCol);
        vbox.getChildren().addAll(table, hb);
    }

    public void setBooks(List<Book> books, String bookType) {
        this.books = FXCollections.observableList(books);
        table.getColumns().clear();
        switch (bookType) {
            case BOOK: table.getColumns().addAll(idCol, titleCol, authorCol, publishedDateCol, priceCol);
                break;
            case PHYSICAL_BOOK: table.getColumns().addAll(idCol, titleCol, authorCol, publishedDateCol, coverCol, priceCol, stockCol);
                break;
            case EBOOK: table.getColumns().addAll(idCol, titleCol, authorCol, publishedDateCol, formatCol, priceCol);
                break;
            case AUDIO_BOOK: table.getColumns().addAll(idCol, titleCol, authorCol, publishedDateCol, runtimeCol, priceCol);
                break;
        }
        table.setItems(this.books);
        table.refresh();
    }

    public void addViewBooksButtonListener(EventHandler<ActionEvent> viewButtonListener) {
        viewBooksButton.setOnAction(viewButtonListener);
    }

    public void addViewPhysicalBooksButtonListener(EventHandler<ActionEvent> viewButtonListener) {
        viewPhysicalBooksButton.setOnAction(viewButtonListener);
    }

    public void addViewEBooksButtonListener(EventHandler<ActionEvent> viewButtonListener) {
        viewEBooksButton.setOnAction(viewButtonListener);
    }

    public void addViewAudioBooksButtonListener(EventHandler<ActionEvent> viewButtonListener) {
        viewAudioBooksButton.setOnAction(viewButtonListener);
    }

    public Book getSelectedItem() {
        return (Book) table.getSelectionModel().getSelectedItem();
    }

    public void addBuyBooksButtonListener(EventHandler<ActionEvent> buyBookButtonListener) {
        buyBookButton.setOnAction(buyBookButtonListener);
    }

    public void setActionTargetText(String text, Color color){
        this.actionTarget.setText(text);
        this.actionTarget.setFill(color);
    }
}
