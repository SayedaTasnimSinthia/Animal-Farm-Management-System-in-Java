CREATE DATABASE animalfarm360;


USE animalfarm360;


CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    review TEXT NOT NULL,
    rating INT NOT NULL
);



CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    email VARCHAR(100),
    employeeId INT UNIQUE NOT NULL,
    department VARCHAR(100),
    position VARCHAR(100),
    doj DATE,
    employeetype VARCHAR(50),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2),
    emergencyName VARCHAR(100),
    emergencyPhone VARCHAR(20),
    image VARCHAR(255)
);



CREATE TABLE attendance (
    employeeId INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    attendanceStatus VARCHAR(20) NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (employeeId) REFERENCES employee(employeeId),
    CONSTRAINT unique_employee_date UNIQUE (employeeId, date)
);



CREATE TABLE salary (
    employeeId INT NOT NULL,
    monthYear VARCHAR(7) NOT NULL,
    salaryAmount DOUBLE NOT NULL,
    status ENUM('Paid', 'Unpaid') DEFAULT 'Unpaid',
    UNIQUE(employeeId, monthYear),
    FOREIGN KEY (employeeId) REFERENCES employee(employeeId)
);



CREATE TABLE bookings (
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    booking_date DATE NOT NULL,
    booking_time VARCHAR(10) NOT NULL,
    visitors INT NOT NULL,
    notes TEXT,
    contacted BOOLEAN DEFAULT FALSE,
    approved BOOLEAN DEFAULT FALSE
);



CREATE TABLE animal (
    animalId INT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    breed VARCHAR(100),
    dob DATE,
    gender VARCHAR(10),
    healthStatus VARCHAR(50),
    notes TEXT NULL,
    productivity VARCHAR(50),
    milkYield DECIMAL(10,2),
    eggCount INT,
    meatYield DECIMAL(10,2),
    wool DECIMAL(10,2),
    saleStatus VARCHAR(20),
    salePrice DECIMAL(10,2),
    systemFlag VARCHAR(20),
    image VARCHAR(255)
);



CREATE TABLE vaccinationrecord (
    id INT AUTO_INCREMENT PRIMARY KEY,
    animalId INT NOT NULL,
    vaccineName VARCHAR(100) NOT NULL,
    dateGiven DATE,
    nextDueDate DATE,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (animalId) REFERENCES animal(animalId)
);



CREATE TABLE animal_product (
    productId INT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    productType VARCHAR(50) NOT NULL,
    breed VARCHAR(50),
    sourceAnimal VARCHAR(50),
    productionDate DATE,
    quantity DECIMAL(10,2),
    unit VARCHAR(20),
    salePrice DECIMAL(10,2),
    notes TEXT,
    image VARCHAR(255),
    status ENUM('Active', 'Inactive') DEFAULT 'Active'
);



CREATE TABLE buyers (
    buyerId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(11),
    password VARCHAR(255) NOT NULL
);



CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_id INT NOT NULL,
    product_type ENUM('animal', 'animal_product') NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES buyers(buyerId)
);



CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    buyer_name VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    FOREIGN KEY (buyer_id) REFERENCES buyers(buyerId)
);



CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_type ENUM('animal', 'animal_product') NOT NULL,
    product_id INT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status ENUM('Pending', 'Confirmed', 'Cancelled') DEFAULT 'Pending',
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);


