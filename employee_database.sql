-- Create the database
CREATE DATABASE IF NOT EXISTS employeeInfo;

-- Use the newly created database
USE employeeInfo;


-- Create employee table with required fields
CREATE TABLE IF NOT EXISTS employee (
    employee_id INT PRIMARY KEY,
    employee_name VARCHAR(20) NOT NULL,
    employee_password VARCHAR(10) NOT NULL,
    employee_salary DECIMAL(10, 2) NOT NULL,
    employee_department VARCHAR(10) NOT NULL
);

-- Insert employee records
INSERT INTO employee (employee_id, employee_name, employee_password, employee_salary, employee_department) VALUES
(101, 'Rupam Manna', 'pass1234', 10000.00, 'CSE'),
(102, 'Satyajit Sasmal', 'pass1234', 12000.00, 'ECE'),
(103, 'Sougata Manna', 'pass1234', 150000.00, 'CE'),
(104, 'Vidisha Das', 'pass1234', 160000.00, 'CSE'),
(105, 'Subhadip Mandal', 'pass1234', 200000.00, 'ME'),
(106, 'Suvankar Pramanik', 'pass1234', 500000.00, 'CSE');


-- Make employee_id auto-incrementing
ALTER TABLE employee
MODIFY COLUMN employee_id INT AUTO_INCREMENT;

-- Add last_login column with default and auto-update timestamp
ALTER TABLE employee
ADD COLUMN last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;


-- View all employee records
SELECT * FROM employee;
