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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project1.model.AudioBook;
import project1.model.Book;
import project1.model.EBook;
import project1.model.PhysicalBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static project1.database.Constants.Tables.*;

public class EmployeeView {
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
    private HBox hbText;
    private HBox hbReport;
    private Button viewBooksButton;
    private Button viewAudioBooksButton;
    private Button viewEBooksButton;
    private Button viewPhysicalBooksButton;
    private Button sellBookButton;
    private Button addBookButton;
    private Button deleteBookButton;
    private Button editBookButton;
    private Button createReportButton;
    private Button logOutButton;
    private Text actionTarget;

    private ObservableList<Book> books;
    private String bookType;

    private final TextField addId = new TextField();
    private final TextField addTitle = new TextField();
    private final TextField addPublishedDate = new TextField();
    private final TextField addAuthor = new TextField();
    private final TextField addPrice = new TextField();
    private final TextField addCover = new TextField();
    private final TextField addStock = new TextField();
    private final TextField addFormat = new TextField();
    private final TextField addRuntime = new TextField();
    private List<TextField> textFields = new ArrayList<>();
    private List<TableColumn> tableColumns = new ArrayList<>();

    public EmployeeView(Stage window) {
        window.setTitle("Book Store");
        window.setWidth(900);
        window.setHeight(600);

        VBox vbox = new VBox();
        initializeVBox(vbox);

        Scene scene = new Scene(new Group());

        initializeFields(vbox);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        window.setScene(scene);
        window.show();
    }

    private void initializeVBox(VBox vbox){
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
    }

    private void setTable(){
        table = new TableView<>();
        table.setEditable(true);

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

        tableColumns.add(idCol);
        tableColumns.add(titleCol);
        tableColumns.add(authorCol);
        tableColumns.add(publishedDateCol);
        tableColumns.add(priceCol);
        tableColumns.add(coverCol);
        tableColumns.add(stockCol);
        tableColumns.add(formatCol);
        tableColumns.add(runtimeCol);
    }

    private void setButtons() {
        viewBooksButton = new Button("View books");
        viewAudioBooksButton = new Button("View audio books");
        viewEBooksButton = new Button("View ebooks");
        viewPhysicalBooksButton = new Button("View physical books");
        sellBookButton = new Button("Sell Book");
        createReportButton = new Button("Create report");
        deleteBookButton = new Button("Delete book");
        addBookButton = new Button("Add");
        editBookButton = new Button("Edit");
        logOutButton = new Button("Log out");
    }

    private void setTextFields(){
        addId.setPromptText("Id");
        addId.setMaxWidth(idCol.getPrefWidth());

        addTitle.setPromptText("Title");
        addTitle.setMaxWidth(titleCol.getPrefWidth());

        addPublishedDate.setPromptText("Published Date");
        addPublishedDate.setMinWidth(publishedDateCol.getPrefWidth());

        addAuthor.setPromptText("Author");
        addAuthor.setMaxWidth(authorCol.getPrefWidth());

        addPrice.setPromptText("Price");
        addPrice.setMaxWidth(priceCol.getPrefWidth());

        addCover.setPromptText("Cover");
        addCover.setMaxWidth(coverCol.getPrefWidth());

        addStock.setPromptText("Stock");
        addStock.setMaxWidth(stockCol.getPrefWidth());

        addRuntime.setPromptText("Runtime");
        addRuntime.setMaxWidth(runtimeCol.getPrefWidth());

        addFormat.setPromptText("Format");
        addFormat.setMaxWidth(formatCol.getPrefWidth());

        addCover.setVisible(false);
        addStock.setVisible(false);
        addFormat.setVisible(false);
        addRuntime.setVisible(false);

        addCover.setManaged(false);
        addStock.setManaged(false);
        addFormat.setManaged(false);
        addRuntime.setManaged(false);

        textFields.add(addId);
        textFields.add(addTitle);
        textFields.add(addAuthor);
        textFields.add(addPublishedDate);
        textFields.add(addPrice);
        textFields.add(addCover);
        textFields.add(addStock);
        textFields.add(addFormat);
        textFields.add(addRuntime);
    }

    private void initializeFields(VBox vbox){
        bookType = BOOK;
        hb = new HBox();
        hbText = new HBox();
        hbReport = new HBox();

        setTable();
        setButtons();
        setTextFields();

        titleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> t) {
                Book book = ((Book) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                System.out.println(book);
                System.out.println(t.getNewValue());
            }
        });

        actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);

        hb.getChildren().addAll(viewBooksButton, viewPhysicalBooksButton, viewAudioBooksButton, viewEBooksButton, sellBookButton, actionTarget, deleteBookButton);
        hb.setSpacing(3);

        hbText.getChildren().addAll(addId, addTitle, addAuthor, addPublishedDate, addPrice, addCover, addStock, addFormat, addRuntime, addBookButton, editBookButton);
        hbReport.getChildren().addAll(createReportButton, logOutButton);

        table.getColumns().addAll(idCol, titleCol, authorCol, publishedDateCol);
        vbox.getChildren().addAll(table, hb, hbText, hbReport);
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

    public void addSellBooksButtonListener(EventHandler<ActionEvent> sellBookButtonListener) {
        sellBookButton.setOnAction(sellBookButtonListener);
    }

    public void addCreateReportButtonListener(EventHandler<ActionEvent> createReportButtonListener) {
        createReportButton.setOnAction(createReportButtonListener);
    }

    public void addDeleteBooksButtonListener(EventHandler<ActionEvent> deleteBookButtonListener) {
        deleteBookButton.setOnAction(deleteBookButtonListener);
    }
    public void addAddBooksButtonListener(EventHandler<ActionEvent> addBookButtonListener) {
        addBookButton.setOnAction(addBookButtonListener);
    }

    public void addEditBooksButtonListener(EventHandler<ActionEvent> editBookButtonListener) {
        editBookButton.setOnAction(editBookButtonListener);
    }

    public void addLogOutButtonListener(EventHandler<ActionEvent> buttonListener) {
        logOutButton.setOnAction(buttonListener);
    }

    public List<TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void setActionTargetText(String text, Color color){
        this.actionTarget.setText(text);
        this.actionTarget.setFill(color);
    }

    public void clearTextFields() {
        addFormat.clear();
        addId.clear();
        addTitle.clear();
        addAuthor.clear();
        addPublishedDate.clear();
        addStock.clear();
        addPrice.clear();
        addFormat.clear();
        addRuntime.clear();
        addCover.clear();
    }

    public List<TextField> getTextFields() {
        return textFields;
    }
}
