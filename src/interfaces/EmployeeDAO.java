package src.interfaces;

import java.util.List;

import src.models.Employee;
import src.models.PayStatement;

public interface EmployeeDAO {
    void insertEmployee(Employee employee);
    Employee searchEmployeeById(int empId);
    Employee searchEmployeeByName(String name);
    Employee searchEmployeeBySSN(String ssn);
    void updateEmployee(Employee employee);
    void updateSalaryByRange(double minSalary, double maxSalary, double percentage);
    void deleteEmployee(int empId);
    List<Employee> getAllEmployees();
    List<PayStatement> getPayStatements(int empId);
    List<String> getTotalPayByJobTitle();
    List<String> getTotalPayByDivision();
}
