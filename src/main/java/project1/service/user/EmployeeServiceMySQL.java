package project1.service.user;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import project1.model.EmployeeReport;
import project1.repository.user.UserRepository;

import java.io.FileNotFoundException;

public class EmployeeServiceMySQL implements EmployeeService {
    private final UserRepository userRepository;

    public EmployeeServiceMySQL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public boolean updateSales(Long id, int price) {
        return userRepository.updateSales(id, price);
    }

    @Override
    public void createReport(Long id) {
        try {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter("report.pdf"));
            Document document = new Document(pdfDocument);
            Table table = new Table(3);

            table.addHeaderCell("Employee").setWidth(60);
            table.addHeaderCell("Books sold").setWidth(40);
            table.addHeaderCell("Income").setWidth(60);

            EmployeeReport report = userRepository.findReportById(id);
            table.addCell(report.getUsername()).setWidth(60);
            table.addCell(String.valueOf(report.getBooksSold())).setWidth(40);
            table.addCell(String.valueOf(report.getIncome())).setWidth(60);

            document.add(table);
            document.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
