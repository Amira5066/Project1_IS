package project1;

import project1.controller.ClientController;
import project1.controller.LoginController;
import project1.database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import project1.model.validator.UserValidator;
import project1.repository.book.BookRepository;
import project1.repository.book.BookRepositoryCacheDecorator;
import project1.repository.book.BookRepositoryMySQL;
import project1.repository.book.Cache;
import project1.repository.security.RightsRolesRepository;
import project1.repository.security.RightsRolesRepositoryMySQL;
import project1.repository.user.UserRepository;
import project1.repository.user.UserRepositoryMySQL;;
import project1.service.book.BookService;
import project1.service.book.BookServiceImpl;
import project1.service.user.AuthenticationService;
import project1.service.user.AuthenticationServiceMySQL;
import project1.view.ClientView;
import project1.view.LoginView;

import java.sql.Connection;

import static project1.database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    private static Stage window;
    private static BookService bookService;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final BookRepository bookRepository =new BookRepositoryMySQL(connection);
        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository);

        bookService = new BookServiceImpl(bookRepository);

        final LoginView loginView = new LoginView(window);


        final UserValidator userValidator = new UserValidator(userRepository);

        new LoginController(loginView, authenticationService, userValidator);
    }

    public static void switchToClientView() {
        final ClientView clientView = new ClientView(window);
        new ClientController(clientView, bookService);

    }
}