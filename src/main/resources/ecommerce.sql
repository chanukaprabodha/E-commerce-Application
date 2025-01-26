CREATE DATABASE ecommerce;

USE ecommerce;

CREATE TABLE categories (
                            category_id VARCHAR(25) PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT
);

CREATE TABLE products (
                          product_id VARCHAR(25) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          category_id VARCHAR(25) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          description TEXT,
                          image_url VARCHAR(255),
                          FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE users (
                       user_id VARCHAR(25) PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       role ENUM('admin', 'customer') DEFAULT 'customer',
                       active BOOLEAN DEFAULT TRUE
);

CREATE TABLE orders (
                        order_id VARCHAR(25) PRIMARY KEY,
                        user_id VARCHAR(25) NOT NULL,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        total_amount DECIMAL(10, 2) NOT NULL,
                        status ENUM('Pending', 'Processing', 'Shipped', 'Delivered', 'Cancelled') DEFAULT 'Pending',
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_details (
                               order_detail_id VARCHAR(25) PRIMARY KEY,
                               order_id VARCHAR(25) NOT NULL,
                               product_id VARCHAR(25) NOT NULL,
                               quantity INT NOT NULL,
                               price DECIMAL(10, 2) NOT NULL,
                               FOREIGN KEY (order_id) REFERENCES orders(order_id),
                               FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE cart (
                      cart_id VARCHAR(25) PRIMARY KEY,
                      user_id VARCHAR(25) NOT NULL,
                      product_id VARCHAR(25) NOT NULL,
                      quantity INT NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES users(user_id),
                      FOREIGN KEY (product_id) REFERENCES products(product_id)
);