package project1.model.builder;

import project1.model.EmployeeReport;

public class EmployeeReportBuilder {
    private EmployeeReport employee;

    public EmployeeReportBuilder(){
        employee = new EmployeeReport();
    }
    public EmployeeReportBuilder setUsername(String username) {
        this.employee.setUsername(username);
        return this;
    }

    public EmployeeReportBuilder setBooksSold(int sales) {
        this.employee.setBooksSold(sales);
        return this;
    }

    public EmployeeReportBuilder setIncome(int income) {
        this.employee.setIncome(income);
        return this;
    }

    public EmployeeReport build(){
        return employee;
    }
}
