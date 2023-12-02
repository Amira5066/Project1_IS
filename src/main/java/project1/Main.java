package project1;

import project1.controller.AdminController;
import project1.controller.CustomerController;
import project1.controller.EmployeeController;
import project1.controller.LoginController;
import project1.database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import project1.model.validator.UserValidator;
import project1.repository.book.BookRepository;
import project1.repository.book.BookRepositoryMySQL;
import project1.repository.security.RightsRolesRepository;
import project1.repository.security.RightsRolesRepositoryMySQL;
import project1.repository.user.EmployeeRepository;
import project1.repository.user.EmployeeRepositoryMySQL;
import project1.repository.user.UserRepository;
import project1.repository.user.UserRepositoryMySQL;;
import project1.service.book.BookService;
import project1.service.book.BookServiceImpl;
import project1.service.user.*;
import project1.view.AdminView;
import project1.view.CustomerView;
import project1.view.EmployeeView;
import project1.view.LoginView;

import java.sql.Connection;

import static project1.database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    private static Stage window;
    private static BookService bookService;
    private static EmployeeService employeeService;
    private static AdminService adminService;
    private static LoginView loginView;
    private static UserValidator userValidator;
    private static UserRepository userRepository;
    private static AuthenticationService authenticationService;
    private static EmployeeRepository employeeRepository;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final BookRepository bookRepository =new BookRepositoryMySQL(connection);
        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        employeeRepository = new EmployeeRepositoryMySQL(connection, rightsRolesRepository);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        employeeService = new EmployeeServiceMySQL(employeeRepository);

        authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository);

        userValidator = new UserValidator(userRepository);

        bookService = new BookServiceImpl(bookRepository);
        adminService = new AdminServiceMySQL(userRepository, rightsRolesRepository, employeeRepository);
        switchToLoginView();
    }

    public static void switchToCustomerView() {
        final CustomerView customerView = new CustomerView(window);
        new CustomerController(customerView, bookService);
    }

    public static void switchToEmployeeView() {
        final EmployeeView employeeView = new EmployeeView(window);
        new EmployeeController(employeeView, bookService, employeeService);
    }

    public static void switchToAdminView() {
        final AdminView adminView = new AdminView(window);
        new AdminController(adminView, adminService);
    }
    public static void switchToLoginView() {
        loginView = new LoginView(window);

        new LoginController(loginView, authenticationService, userValidator);
    }
}