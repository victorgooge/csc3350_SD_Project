package models;

public class PayStatement {
    private int statementId;
    private int empId;
    private String payDate;
    private double amount;

    public PayStatement(int statementId, int empId, String payDate, double amount) {
        this.statementId = statementId;
        this.empId = empId;
        this.payDate = payDate;
        this.amount = amount;
    }

    public int getStatementId() { return statementId; }
    public void setStatementId(int statementId) { this.statementId = statementId; }

    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }

    public String getPayDate() { return payDate; }
    public void setPayDate(String payDate) { this.payDate = payDate; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
