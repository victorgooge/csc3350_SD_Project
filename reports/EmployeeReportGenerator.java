package reports;

import interfaces.ReportGenerator;
import interfaces.EmployeeDAO;

public class EmployeeReportGenerator implements ReportGenerator {
    private EmployeeDAO employeeDAO;

    public EmployeeReportGenerator(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void generateFullEmployeeReport() {
        System.out.println("=== Full-Time Employee Information with Pay Statement History ===");
        employeeDAO.getAllEmployees().forEach(System.out::println);
    }

    @Override
    public void generateTotalPayByJobTitle() {
        System.out.println("=== Total Pay by Job Title ===");
        employeeDAO.getTotalPayByJobTitle().forEach(System.out::println);
    }

    @Override
    public void generateTotalPayByDivision() {
        System.out.println("=== Total Pay by Division ===");
        employeeDAO.getTotalPayByDivision().forEach(System.out::println);
    }
}
