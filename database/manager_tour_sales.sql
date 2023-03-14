CREATE DATABASE manager_tour_sales;

USE manager_tour_sales;

create table role (
id int auto_increment primary key,
name varchar(255)
);
create table tour_guide(
id INT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
email VARCHAR(255),
phone_number VARCHAR(20),
address VARCHAR(255),
`description` text,
`status` bit
);

CREATE TABLE Tours (
id INT PRIMARY KEY,
tour_name VARCHAR(255) NOT NULL,
description text,
location VARCHAR(255),
start_date DATE,
end_date DATE,
price double,
`status` bit
);
create table history_tour(
tour_guide_id int,
tour_id int,
salary double not null,
primary key(tour_guide_id,tour_id),
foreign key(tour_guide_id) references tour_guide(id),
foreign key(tour_id) references tours(id)
);
CREATE TABLE Customers (
id INT PRIMARY KEY,
customer_name VARCHAR(255) NOT NULL,
date_of_birth date,
paypal_account varchar(255),
email VARCHAR(255),
phone_number VARCHAR(20),
address VARCHAR(255)
);
CREATE TABLE Employees (
id INT PRIMARY KEY,
employee_name VARCHAR(255) NOT NULL,
email VARCHAR(255),
phone_number VARCHAR(20),
address VARCHAR(255),
date_of_birth DATE
);
create table user_roles (
employee_id int ,
roles_id int ,
primary key(employee_id,roles_id),
foreign key(employee_id) references employees(id),
foreign key(roles_id) references role(id)
);
CREATE TABLE Bookings (
id INT PRIMARY KEY,
tour_id INT,
customer_id INT,
employee_id INT,
tour_guide_id INT,
booking_date DATE,
slot int,
FOREIGN KEY (tour_id) REFERENCES Tours(id),
FOREIGN KEY (customer_id) REFERENCES Customers(id),
FOREIGN KEY (employee_id) REFERENCES Employees(id)
);
create table position (
id INT PRIMARY KEY,
name varchar(50),
employee_id INT,
foreign key (employee_id) references employees(id)
);