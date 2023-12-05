package project1.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project1.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static project1.database.Constants.Tables.*;
import static project1.database.Constants.Tables.AUDIO_BOOK;

public class AdminView {
    private TableView table;
    private TableColumn idCol;
    private TableColumn usernameCol;
    private HBox hb;
    private HBox hbAddEditEmp;
    private HBox hbLogOut;
    private Button viewEmployeesButton;
    private Button deleteEmployeeButton;
    private Button addEmployeeButton;
    private Button editEmployeeButton;
    private Button createReportButton;
    private Button logOutButton;

    private Text actionTarget;

    private ObservableList<User> employees;
    private final TextField addUsername = new TextField();


    public AdminView(Stage window) {
        window.setTitle("Staff");
        window.setWidth(600);
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
        hb = new HBox();
        hbAddEditEmp = new HBox();
        hbLogOut = new HBox();

        table = new TableView<>();
        table.setEditable(false);

        idCol = new TableColumn("Id");
        idCol.setMaxWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));

        usernameCol = new TableColumn("Username");
        usernameCol.setMinWidth(200);
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));

        addUsername.setPromptText("Username");
        addUsername.setMinWidth(usernameCol.getPrefWidth());

        viewEmployeesButton = new Button("View employees");
        deleteEmployeeButton = new Button("Delete");
        addEmployeeButton = new Button("Add");
        editEmployeeButton = new Button("Edit");
        logOutButton = new Button("Log out");
        createReportButton = new Button("Create report");

        actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);

        hb.getChildren().addAll(viewEmployeesButton);
        hb.setSpacing(3);

        hbAddEditEmp.getChildren().addAll(addUsername, addEmployeeButton, actionTarget, editEmployeeButton, deleteEmployeeButton);
        hbAddEditEmp.setSpacing(3);

        hbLogOut.getChildren().addAll(createReportButton, logOutButton);

        table.getColumns().addAll(idCol, usernameCol);
        vbox.getChildren().addAll(table, hb, hbAddEditEmp, hbLogOut);
    }
    public void addDeleteEmployeeButtonListener(EventHandler<ActionEvent> deleteEmployeeButtonListener) {
        deleteEmployeeButton.setOnAction(deleteEmployeeButtonListener);
    }

    public void addViewEmployeesButtonListener(EventHandler<ActionEvent> viewButtonListener) {
        viewEmployeesButton.setOnAction(viewButtonListener);
    }

    public User getSelectedItem() {
        return (User) table.getSelectionModel().getSelectedItem();
    }

    public void addEmployeeButtonListener(EventHandler<ActionEvent> buttonListener) {
        addEmployeeButton.setOnAction(buttonListener);
    }

    public void addEditEmployeeButtonListener(EventHandler<ActionEvent> buttonListener) {
        editEmployeeButton.setOnAction(buttonListener);
    }

    public void addLogOutButtonListener(EventHandler<ActionEvent> buttonListener) {
        logOutButton.setOnAction(buttonListener);
    }

    public void addCreateReportButtonListener(EventHandler<ActionEvent> buttonListener) {
        createReportButton.setOnAction(buttonListener);
    }

    public void setActionTargetText(String text, Color color){
        this.actionTarget.setText(text);
        this.actionTarget.setFill(color);
    }

    public TextField getTextField() {
        return addUsername;
    }

    public void setEmployees(List<User> employees) {
        this.employees = FXCollections.observableList(employees);
        table.setItems(this.employees);

        table.refresh();
    }
}
