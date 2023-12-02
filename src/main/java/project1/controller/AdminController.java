package project1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import project1.model.User;
import project1.service.user.AdminService;
import project1.view.AdminView;

public class AdminController {
    private AdminView adminView;
    private static AdminService adminService;
    private ViewEmployeeButtonListener viewEmployeeButtonListener = new ViewEmployeeButtonListener();

    public AdminController(AdminView adminView, AdminService adminService) {
        this.adminView = adminView;
        this.adminService = adminService;

        this.adminView.addDeleteEmployeeButtonListener(new DeleteEmployeeButtonListener());
        this.adminView.addEmployeeButtonListener(new AddEmployeeButtonListener());
        this.adminView.addViewEmployeesButtonListener(viewEmployeeButtonListener);
        this.adminView.addEditEmployeeButtonListener(new EditEmployeeButtonListener());
        this.adminView.addLogOutButtonListener(new LogOutButtonListener());
        this.adminView.addCreateReportButtonListener(new CreateReportButtonListener());
    }

    private class EditEmployeeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            User user = adminView.getSelectedItem();
            adminService.editEmployee(adminView.getTextField().getText(), user.getId());
            viewEmployeeButtonListener.handle(new ActionEvent());
        }
    }

    private class DeleteEmployeeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            User user = adminView.getSelectedItem();
            adminService.deleteEmployee(user.getId());
            viewEmployeeButtonListener.handle(new ActionEvent());
        }
    }

    private class AddEmployeeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            adminService.addEmployee(adminView.getTextField().getText());
            viewEmployeeButtonListener.handle(new ActionEvent());
        }
    }

    private class ViewEmployeeButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            adminView.setEmployees(adminService.findAllEmployees());
        }
    }

    private class CreateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            adminService.createReport();
            adminService.createReport();
        }
    }
}
