# CSC3350 Final Project



Minimal Java console application to manage employee data with a MySQL database.

---

## How to Run Locally


### 1. Install Requirements

- Java 17+ installed
- MySQL Server running locally
- MySQL JDBC Connector JAR (download from MySQL site)

---

### 2. Set Up the Database

#### Using DBeaver

1. **Open DBeaver**
   - Launch **DBeaver**.

2. **Connect to MySQL**
   - Click **Database → New Database Connection**.
   - Choose **MySQL**.
   - Enter your connection details:
     - **Host**: `localhost`
     - **Port**: `3306`
     - **Username**: `root`
     - **Password**: (your MySQL password)
   - Test the connection and finish.

3. **Load the Table Script**
   - Click **SQL Editor → Open SQL Script**.
   - Load the file:
     ```
     employee_management_schema.sql
     ```
     (provided in the repository)
   
   - **OR** manually run the following SQL:

   ```sql
   CREATE DATABASE IF NOT EXISTS employeeData;
   USE employeeData;

   CREATE TABLE employees (
       empid INT PRIMARY KEY,
       name VARCHAR(100),
       ssn VARCHAR(20),
       job_title VARCHAR(100),
       division VARCHAR(100),
       salary DOUBLE
   );

   CREATE TABLE pay_statements (
       statement_id INT PRIMARY KEY AUTO_INCREMENT,
       empid INT,
       pay_date DATE,
       amount DOUBLE,
       FOREIGN KEY (empid) REFERENCES employees(empid)
   );
   ```

---

### 3. Execute the Script

- Highlight the SQL script and click **Run**.

DBeaver will create:
- A new database: `employeeData`
- Two tables: `employees` and `pay_statements`

---

### 4. Configure Database Connection in `MainApp.java`

Update your connection string:

```java
Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/employeeData",
    "root", // your username
    "password" // your password
);
```

Replace `"root"` and `"password"` with your actual MySQL login credentials.

---

### 5. Compile the Code

Using terminal:

```bash
javac -d bin src/**/*.java
```

Or build the project normally if using an IDE like IntelliJ or Eclipse.

---

### 6. Run the Program

Using terminal:

```bash
java -cp bin MainApp
```

Or right-click `MainApp.java` → **Run**.

You should see the console menu:

```
=== Employee Management System ===
1. Insert New Employee
2. Search Employee
3. Update Employee
4. Update Salaries by Range
5. Generate Reports
6. Exit
Enter your choice:
```

---

## Running Unit Tests

JUnit tests are located in:

```plaintext
test/dao/MySQLEmployeeDAOTest.java
```

- Make sure **JUnit 5** is installed.
- Run tests to verify database operations (insert, search, update, delete).

---

# Contributors

- Tanaka Makuvaza
- William Shay
- Syed Shoyeb
- Victor Googe
- Shan Patel

