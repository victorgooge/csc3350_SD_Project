package ui;

import interfaces.UserInterface;
import interfaces.EmployeeDAO;
import interfaces.ReportGenerator;
import models.Employee;

import java.util.Scanner;

public class ConsoleUI implements UserInterface {
    private EmployeeDAO employeeDAO;
    private ReportGenerator reportGenerator;
    private Scanner scanner;

    public ConsoleUI(EmployeeDAO employeeDAO, ReportGenerator reportGenerator) {
        this.employeeDAO = employeeDAO;
        this.reportGenerator = reportGenerator;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. Insert New Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Update Salaries by Range");
            System.out.println("5. Generate Reports");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> insertEmployee();
                case 2 -> searchEmployee();
                case 3 -> updateEmployee();
                case 4 -> updateSalaries();
                case 5 -> generateReports();
                case 6 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void insertEmployee() {
        System.out.println("Enter Employee ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Name:");
        String name = scanner.nextLine();
        System.out.println("Enter SSN (no dashes):");
        String ssn = scanner.nextLine();
        System.out.println("Enter Job Title:");
        String job = scanner.nextLine();
        System.out.println("Enter Division:");
        String div = scanner.nextLine();
        System.out.println("Enter Salary:");
        double salary = scanner.nextDouble();

        Employee emp = new Employee(id, name, ssn, job, div, salary);
        employeeDAO.insertEmployee(emp);
        System.out.println("Employee added successfully!");
    }

    private void searchEmployee() {
        System.out.println("Search by: 1. ID 2. Name 3. SSN");
        int option = scanner.nextInt();
        scanner.nextLine();
        Employee result = null;
        if (option == 1) {
            System.out.println("Enter Employee ID:");
            result = employeeDAO.searchEmployeeById(scanner.nextInt());
        } else if (option == 2) {
            System.out.println("Enter Name:");
            result = employeeDAO.searchEmployeeByName(scanner.nextLine());
        } else if (option == 3) {
            System.out.println("Enter SSN:");
            result = employeeDAO.searchEmployeeBySSN(scanner.nextLine());
        }

        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("Employee not found.");
        }
    }

    private void updateEmployee() {
        System.out.println("Enter Employee ID to Update:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Employee emp = employeeDAO.searchEmployeeById(id);
        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Enter New Name (current: " + emp.getName() + "):");
        emp.setName(scanner.nextLine());
        System.out.println("Enter New SSN (current: " + emp.getSsn() + "):");
        emp.setSsn(scanner.nextLine());
        System.out.println("Enter New Job Title (current: " + emp.getJobTitle() + "):");
        emp.setJobTitle(scanner.nextLine());
        System.out.println("Enter New Division (current: " + emp.getDivision() + "):");
        emp.setDivision(scanner.nextLine());
        System.out.println("Enter New Salary (current: " + emp.getSalary() + "):");
        emp.setSalary(scanner.nextDouble());

        employeeDAO.updateEmployee(emp);
        System.out.println("Employee updated successfully!");
    }

    private void updateSalaries() {
        System.out.println("Enter Minimum Salary:");
        double min = scanner.nextDouble();
        System.out.println("Enter Maximum Salary:");
        double max = scanner.nextDouble();
        System.out.println("Enter Percentage Increase:");
        double percent = scanner.nextDouble();

        employeeDAO.updateSalaryByRange(min, max, percent);
        System.out.println("Salaries updated successfully!");
    }

    private void generateReports() {
        System.out.println("Choose Report: 1. Full Employee Info 2. Total Pay by Job Title 3. Total Pay by Division");
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> reportGenerator.generateFullEmployeeReport();
            case 2 -> reportGenerator.generateTotalPayByJobTitle();
            case 3 -> reportGenerator.generateTotalPayByDivision();
            default -> System.out.println("Invalid choice.");
        }
    }
}