package project1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import project1.Main;
import project1.model.User;
import project1.model.validator.UserValidator;
import project1.service.user.AuthenticationService;
import project1.view.LoginView;

import java.util.List;

import static project1.database.Constants.Roles.CUSTOMER;
import static project1.database.Constants.Roles.EMPLOYEE;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    private static Long id;


    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserValidator userValidator) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User user = authenticationService.login(username, password);

            if (user == null){
                loginView.setActionTargetText("Invalid Username or password!");
            }else{
                id = user.getId();
                loginView.setActionTargetText("LogIn Successful!");
                switch (user.getRoles().get(0).getRole()) {
                    case CUSTOMER:
                        Main.switchToCustomerView();
                        break;
                    case EMPLOYEE:
                        Main.switchToEmployeeView();
                        break;
                }
            }

        }
    }

    public static Long getId() {
        return id;
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username, password);
            final List<String> errors = userValidator.getErrors();
            if (errors.isEmpty()) {
                if (authenticationService.register(username, password)){
                    loginView.setActionTargetText("Register successfull!");
                }else{
                    loginView.setActionTargetText("Register NOT successfull!");
                }
            } else {
                loginView.setActionTargetText(userValidator.getFormattedErrors());
            }
        }
    }
}
