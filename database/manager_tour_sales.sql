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
create table location (
id INT PRIMARY KEY,
name varchar(255)
);
CREATE TABLE Tours (
id INT PRIMARY KEY,
tour_name VARCHAR(255) NOT NULL,
description text,
location_id int,
start_date DATE,
end_date DATE,
price double,
tour_guide_id int,
tour_guide_cost double,
`status` bit,
foreign key(location_id) references location(id),
foreign key(tour_guide_id) references tour_guide(id)
);
create table user (
id INT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
email VARCHAR(255),
phone_number VARCHAR(20),
address VARCHAR(255),
`description` text,
`status` bit,
date_of_birth DATE,
salary double,
paypal_account varchar(255)
);
create table user_roles (
user_id int ,
roles_id int ,
primary key(user_id,roles_id),
foreign key(user_id) references user(id),
foreign key(roles_id) references role(id)
);
CREATE TABLE Bookings (
id INT PRIMARY KEY,
tour_id INT,
user_id int,
booking_date DATE,
slot int,
FOREIGN KEY (tour_id) REFERENCES Tours(id),
FOREIGN KEY (user_id) REFERENCES user(id)
);