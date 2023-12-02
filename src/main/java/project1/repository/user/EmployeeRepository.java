package project1.repository.user;

import project1.model.EmployeeReport;

import java.util.List;

public interface EmployeeRepository {
    List<EmployeeReport> findAllReports();

    boolean updateSales(Long id, int price);

    EmployeeReport findReportById(Long id);

    boolean addEmployee(Long id);
}
