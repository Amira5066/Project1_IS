package project1.repository.user;

import project1.model.EmployeeReport;
import project1.model.builder.EmployeeReportBuilder;
import project1.repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static project1.database.Constants.Tables.EMPLOYEE_REPORT;
import static project1.database.Constants.Tables.USER;

public class EmployeeRepositoryMySQL implements EmployeeRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public EmployeeRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }
    @Override
    public List<EmployeeReport> findAllReports() {
        List<EmployeeReport> employeeReports = new ArrayList<>();

        String sql = "SELECT username, books_sold, income FROM employee_report JOIN user ON user_id = id;";
        try {
            Statement statement = statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                EmployeeReport report = new EmployeeReportBuilder()
                        .setUsername(resultSet.getString("username"))
                        .setBooksSold(resultSet.getInt("books_sold"))
                        .setIncome(resultSet.getInt("income"))
                        .build();
                employeeReports.add(report);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeReports;
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
    @Override
    public boolean updateSales(Long id, int price) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE employee_report SET books_sold = books_sold + 1, income = income + ?  WHERE user_id = ?");
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
    public boolean addEmployee(Long id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO employee_report(user_id) VALUES(?)");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
