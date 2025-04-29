package ui;

import interfaces.EmployeeDAO;
import interfaces.ReportGenerator;
import interfaces.UserInterface;
import models.Employee;

import java.util.Scanner;

/**
 * console-based user interface for the employee management system.
 */
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

            int choice = getValidIntInput();
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

    // insert new employee
    private void insertEmployee() {
        System.out.println("Enter Employee ID:");
        int id = getValidIntInput();
        scanner.nextLine();

        String name = getNonEmptyStringInput("Enter Name:");
        String ssn = getNonEmptyStringInput("Enter SSN (no dashes):");
        String job = getNonEmptyStringInput("Enter Job Title:");
        String div = getNonEmptyStringInput("Enter Division:");

        System.out.println("Enter Salary:");
        double salary = getValidDoubleInput();

        Employee emp = new Employee(id, name, ssn, job, div, salary);
        employeeDAO.insertEmployee(emp);
        System.out.println("Employee added successfully!");
    }

    // search for an employee
    private void searchEmployee() {
        System.out.println("Search by: 1. ID 2. Name 3. SSN");
        int option = getValidIntInput();
        scanner.nextLine();
        Employee result = null;

        if (option == 1) {
            System.out.println("Enter Employee ID:");
            result = employeeDAO.searchEmployeeById(getValidIntInput());
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

    // update employee information
    private void updateEmployee() {
        System.out.println("Enter Employee ID to Update:");
        int id = getValidIntInput();
        scanner.nextLine();

        Employee emp = employeeDAO.searchEmployeeById(id);
        if (emp == null) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("Enter New Name (current: " + emp.getName() + "):");
        String newName = scanner.nextLine();
        if (!newName.trim().isEmpty()) emp.setName(newName);

        System.out.println("Enter New SSN (current: " + emp.getSsn() + "):");
        String newSsn = scanner.nextLine();
        if (!newSsn.trim().isEmpty()) emp.setSsn(newSsn);

        System.out.println("Enter New Job Title (current: " + emp.getJobTitle() + "):");
        String newJob = scanner.nextLine();
        if (!newJob.trim().isEmpty()) emp.setJobTitle(newJob);

        System.out.println("Enter New Division (current: " + emp.getDivision() + "):");
        String newDiv = scanner.nextLine();
        if (!newDiv.trim().isEmpty()) emp.setDivision(newDiv);

        System.out.println("Enter New Salary (current: " + emp.getSalary() + "):");
        String salaryInput = scanner.nextLine();
        if (!salaryInput.trim().isEmpty()) {
            emp.setSalary(Double.parseDouble(salaryInput));
        }

        employeeDAO.updateEmployee(emp);
        System.out.println("Employee updated successfully!");
    }

    // update salaries for employees within a salary range
    private void updateSalaries() {
        System.out.println("Enter Minimum Salary:");
        double min = getValidDoubleInput();

        System.out.println("Enter Maximum Salary:");
        double max = getValidDoubleInput();

        System.out.println("Enter Percentage Increase:");
        double percent = getValidDoubleInput();

        employeeDAO.updateSalaryByRange(min, max, percent);
        System.out.println("Salaries updated successfully!");
    }

    // generate different reports
    private void generateReports() {
        System.out.println("Choose Report: 1. Full Employee Info 2. Total Pay by Job Title 3. Total Pay by Division");
        int option = getValidIntInput();

        switch (option) {
            case 1 -> reportGenerator.generateFullEmployeeReport();
            case 2 -> reportGenerator.generateTotalPayByJobTitle();
            case 3 -> reportGenerator.generateTotalPayByDivision();
            default -> System.out.println("Invalid choice.");
        }
    }

    // helper: get valid integer input
    private int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // helper: get valid double input
    private double getValidDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number:");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    // helper: get non-empty string input
    private String getNonEmptyStringInput(String prompt) {
        String input;
        do {
            System.out.println(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }
}
