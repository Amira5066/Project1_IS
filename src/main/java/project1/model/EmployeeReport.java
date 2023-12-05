package project1.model;

public class EmployeeReport {
    private String username;
    private int booksSold;
    private int income;
    public EmployeeReport(){}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBooksSold(int booksSold) {
        this.booksSold = booksSold;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getUsername() {
        return username;
    }

    public int getBooksSold() {
        return booksSold;
    }

    public int getIncome() {
        return income;
    }

    @Override
    public String toString() {
        return "EmployeeReport{" +
                "username='" + username + '\'' +
                ", booksSold=" + booksSold +
                ", income=" + income +
                '}';
    }
}
