package project1.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import project1.Main;

public class LogOutButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(javafx.event.ActionEvent event) {
        Main.switchToLoginView();
    }
}
