package project1.service.user;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import project1.model.EmployeeReport;
import project1.model.Role;
import project1.model.User;
import project1.repository.security.RightsRolesRepository;
import project1.repository.user.EmployeeRepository;
import project1.repository.user.UserRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static project1.database.Constants.Roles.CUSTOMER;
import static project1.database.Constants.Roles.EMPLOYEE;

public class AdminServiceMySQL implements AdminService {
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final EmployeeRepository employeeRepository;

    public AdminServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean addEmployee(String username) {
        List<Role> roles = new ArrayList<>();
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        roles.add(employeeRole);
        User user = userRepository.findByUsername(username);
        rightsRolesRepository.deleteRoles(user.getId());
        rightsRolesRepository.addRolesToUser(user, roles);
        return employeeRepository.addEmployee(user.getId());
    }

    @Override
    public boolean editEmployee(String newUsername, Long id) {
        return userRepository.editUser(newUsername, id);
    }

    @Override
    public boolean deleteEmployee(Long id) {
        return userRepository.deleteUser(id);
    }

    @Override
    public List<User> findAllEmployees() {
        return userRepository.findByRole(EMPLOYEE);
    }

    @Override
    public void createReport() {
        PdfDocument pdfDocument = null;
        try {
            pdfDocument = new PdfDocument(new PdfWriter("C:/Users/amira/Desktop/report.pdf"));
            Document document = new Document(pdfDocument);
            Table table = new Table(3);

            table.addHeaderCell("Employee").setWidth(60);
            table.addHeaderCell("Books sold").setWidth(40);
            table.addHeaderCell("Income").setWidth(60);

            List<EmployeeReport> reports = employeeRepository.findAllReports();
            for (EmployeeReport report : reports) {
                table.addCell(report.getUsername()).setWidth(60);
                table.addCell(String.valueOf(report.getBooksSold())).setWidth(40);
                table.addCell(String.valueOf(report.getIncome())).setWidth(60);
            }

            document.add(table);
            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
