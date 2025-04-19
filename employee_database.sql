create database employeeInfo;
use employeeInfo;
CREATE TABLE employee (
    employee_id INT PRIMARY KEY,
    employee_name VARCHAR(20) NOT NULL,
    employee_password VARCHAR(10) NOT NULL,
    employee_salary decimal(10,2) NOT NULL,
    employee_department VARCHAR(10) NOT NULL
);


INSERT INTO employee (employee_id, employee_name, employee_password, employee_salary, employee_department) VALUES
(101, 'Rupam Manna', 'pass1234', 10000.00, 'CSE'),
(102, 'Satyajit Sasmal', 'pass1234', 12000.00,'ECE'),
(103, 'Sougata Manna', 'pass1234', 150000.00, 'CE'),
(104, 'Bidisha Das', 'pass1234', 160000, 'CSE'),
(105, 'Subhadip Mandal', 'pass1234', 200000.00, 'ME'),
(106, 'Suvankar Pramanik', 'pass1234', 500000.00, 'CSE');


select * from employee;