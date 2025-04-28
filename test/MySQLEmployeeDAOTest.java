package dao;

import models.Employee;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLEmployeeDAOTest {
    private static Connection conn;
    private static MySQLEmployeeDAO dao;

    @BeforeAll
    static void setup() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeeData", "root", "password");
        dao = new MySQLEmployeeDAO(conn);
    }

    @AfterAll
    static void teardown() throws SQLException {
        if (conn != null) conn.close();
    }

    @Test
    void testInsertAndSearchEmployee() {
        Employee employee = new Employee(9999, "Test User", "123456789", "Tester", "QA", 60000);
        dao.insertEmployee(employee);

        Employee found = dao.searchEmployeeById(9999);
        assertNotNull(found);
        assertEquals("Test User", found.getName());
        assertEquals("123456789", found.getSsn());
    }

    @Test
    void testUpdateSalaryByRange() {
        double minSalary = 50000;
        double maxSalary = 70000;
        double percentage = 5.0;

        dao.updateSalaryByRange(minSalary, maxSalary, percentage);

        List<Employee> employees = dao.getAllEmployees();
        for (Employee e : employees) {
            if (e.getSalary() >= minSalary && e.getSalary() < maxSalary) {
                assertTrue(e.getSalary() > minSalary);  // basic check if salary increased
            }
        }
    }

    @Test
    void testUpdateEmployee() {
        Employee employee = dao.searchEmployeeById(9999);
        assertNotNull(employee);

        employee.setName("Updated User");
        dao.updateEmployee(employee);

        Employee updated = dao.searchEmployeeById(9999);
        assertEquals("Updated User", updated.getName());
    }

    @Test
    void testDeleteEmployee() {
        dao.deleteEmployee(9999);
        Employee deleted = dao.searchEmployeeById(9999);
        assertNull(deleted);
    }
}
