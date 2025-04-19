# Employee Management System (Java + MySQL)

This is a Java-based desktop application that allows an admin or employee to manage employee records using a graphical user interface (GUI) built with Java Swing. It connects to a MySQL database to perform CRUD operations.

## 💡 Features

- 🔐 Secure employee login using prepared statements
- 📋 View employee records (Admin view)
- 🔄 Update or delete employee details
- 🔑 Reset/change password
- 🕒 Store and view last login time
- ➕ Add new employees
- 🛡️ Admin-only operations secured with a predefined password

## 🛠️ Technologies Used

- Java (JDK 8+)
- Swing (for GUI)
- MySQL (Database)
- JDBC (Java Database Connectivity)

## 🗂️ Database Schema

Make sure you have a MySQL database named `employeeInfo` with a table `employee` structured as follows:

```sql
CREATE DATABASE employeeInfo;

USE employeeInfo;

CREATE TABLE employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(255),
    employee_password VARCHAR(255),
    employee_salary DOUBLE,
    employee_department VARCHAR(255),
    last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


