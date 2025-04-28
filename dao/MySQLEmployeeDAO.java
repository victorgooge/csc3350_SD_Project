package dao;

import interfaces.EmployeeDAO;
import models.Employee;
import models.PayStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLEmployeeDAO implements EmployeeDAO {
    private Connection conn;

    public MySQLEmployeeDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertEmployee(Employee employee) {
        String sql = "INSERT INTO employees (empid, name, ssn, job_title, division, salary) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getEmpId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getSsn());
            pstmt.setString(4, employee.getJobTitle());
            pstmt.setString(5, employee.getDivision());
            pstmt.setDouble(6, employee.getSalary());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee searchEmployeeById(int empId) {
        String sql = "SELECT * FROM employees WHERE empid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee searchEmployeeByName(String name) {
        String sql = "SELECT * FROM employees WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee searchEmployeeBySSN(String ssn) {
        String sql = "SELECT * FROM employees WHERE ssn = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET name=?, ssn=?, job_title=?, division=?, salary=? WHERE empid=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getSsn());
            pstmt.setString(3, employee.getJobTitle());
            pstmt.setString(4, employee.getDivision());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setInt(6, employee.getEmpId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSalaryByRange(double minSalary, double maxSalary, double percentage) {
        String sql = "UPDATE employees SET salary = salary + (salary * ? / 100) WHERE salary >= ? AND salary < ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, percentage);
            pstmt.setDouble(2, minSalary);
            pstmt.setDouble(3, maxSalary);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE empid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                employees.add(extractEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<PayStatement> getPayStatements(int empId) {
        List<PayStatement> statements = new ArrayList<>();
        String sql = "SELECT * FROM pay_statements WHERE empid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                statements.add(new PayStatement(
                        rs.getInt("statement_id"),
                        rs.getInt("empid"),
                        rs.getString("pay_date"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statements;
    }

    @Override
    public List<String> getTotalPayByJobTitle() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT job_title, SUM(salary) AS total_salary FROM employees GROUP BY job_title";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(rs.getString("job_title") + ": $" + rs.getDouble("total_salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<String> getTotalPayByDivision() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT division, SUM(salary) AS total_salary FROM employees GROUP BY division";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(rs.getString("division") + ": $" + rs.getDouble("total_salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private Employee extractEmployee(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("empid"),
            rs.getString("name"),
            rs.getString("ssn"),
            rs.getString("job_title"),
            rs.getString("division"),
            rs.getDouble("salary")
        );
    }
}
