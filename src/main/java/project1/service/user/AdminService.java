package project1.service.user;

import project1.model.User;

import java.util.List;

public interface AdminService {
    boolean addEmployee(String username);
    boolean editEmployee(String newUsername, Long id);
    boolean deleteEmployee(Long id);
    List<User> findAllEmployees();
    void createReport();
}
