package project1.repository.user;

import project1.model.Book;
import project1.model.EmployeeReport;
import  project1.model.User;
import project1.model.builder.EmployeeReportBuilder;
import  project1.model.builder.UserBuilder;
import  project1.repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static project1.database.Constants.Tables.EMPLOYEE_REPORT;
import static  project1.database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            userResultSet.next();

            User user = new UserBuilder()
                    .setId(userResultSet.getLong("id"))
                    .setUsername(userResultSet.getString("username"))
                    .setPassword(userResultSet.getString("password"))
                    .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                    .build();

            return user;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {
        try {
            Statement statement = connection.createStatement();

            String fetchUserSql =
                    "Select * from `" + USER + "` where `username`=\'" + email + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            return userResultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSales(Long id, int price) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE employee_report SET books_sold = books_sold + 1, income = income + ?  WHERE user_id = ?", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, price);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public EmployeeReport findReportById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String fetchEmployeeSql =
                    "SELECT username, books_sold, income FROM " + EMPLOYEE_REPORT + " JOIN " + USER + " ON user_id = id WHERE id = " + id + ";";
            ResultSet userResultSet = statement.executeQuery(fetchEmployeeSql);
            userResultSet.next();

            EmployeeReport report = new EmployeeReportBuilder()
                    .setUsername(userResultSet.getString("username"))
                    .setBooksSold(userResultSet.getInt("books_sold"))
                    .setIncome(userResultSet.getInt("income"))
                    .build();

            return report;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}