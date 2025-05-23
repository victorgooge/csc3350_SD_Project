package src;
import src.dao.MySQLEmployeeDAO;
import src.reports.EmployeeReportGenerator;
import src.ui.ConsoleUI;

import java.sql.Connection;
import java.sql.DriverManager;

public class MainApp {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employeeData", 
                "root", 
                "Lilly442000#"
            );
            var employeeDAO = new MySQLEmployeeDAO(conn);
            var reportGenerator = new EmployeeReportGenerator(employeeDAO);
            var consoleUI = new ConsoleUI(employeeDAO, reportGenerator);

            consoleUI.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}