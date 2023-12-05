package project1.service.user;

import project1.model.User;

public interface AuthenticationService {
    boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);

}
