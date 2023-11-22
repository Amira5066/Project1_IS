package project1.repository.user;

import project1.model.User;

import java.util.*;

public interface UserRepository {

    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    boolean existsByUsername(String username);
}