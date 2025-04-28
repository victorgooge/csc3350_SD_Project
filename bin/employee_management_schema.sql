-- SQL Schema for Employee Management System

-- Create the database
CREATE DATABASE IF NOT EXISTS employeeData;

-- Switch to the database
USE employeeData;

-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
    empid INT PRIMARY KEY,
    name VARCHAR(100),
    ssn VARCHAR(20),
    job_title VARCHAR(100),
    division VARCHAR(100),
    salary DOUBLE
);

-- Create pay_statements table
CREATE TABLE IF NOT EXISTS pay_statements (
    statement_id INT PRIMARY KEY AUTO_INCREMENT,
    empid INT,
    pay_date DATE,
    amount DOUBLE,
    FOREIGN KEY (empid) REFERENCES employees(empid)
);
