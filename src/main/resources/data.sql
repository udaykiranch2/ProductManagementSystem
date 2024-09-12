
-- Insert initial customers
INSERT INTO customer (first_name, last_name, email, phone_number, address)
VALUES ('John', 'Doe', 'john.doe@example.com', '+1234567890', '123 Main St');

INSERT INTO customer (first_name, last_name, email, phone_number, address)
VALUES ('Jane', 'Smith', 'jane.smith@example.com', '+0987654321', '456 High St');

-- Insert initial products
INSERT INTO product (name, description, price, stock_quantity)
VALUES ('Laptop', 'A high-performance laptop', 1500.00, 10);

INSERT INTO product (name, description, price, stock_quantity)
VALUES ('Smartphone', 'A latest model smartphone', 700.00, 20);

-- Insert initial orders
INSERT INTO orders (order_date, status, total_amount, customer_id, product_id)
VALUES (CURRENT_TIMESTAMP, 'PENDING', 1500.00, 1, 1);

INSERT INTO orders (order_date, status, total_amount, customer_id, product_id)
VALUES (CURRENT_TIMESTAMP, 'PENDING', 700.00, 2, 2);

---- Create roles
INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');

-- Create users
INSERT INTO users (username, password) VALUES ('uday', '$2a$12$nDPxHovO/2jUEmlwP967/e0RDCyWKE4ljPS/GuXK64hl3C6ac83gi'); -- password: u@123
INSERT INTO users (username, password) VALUES ('admin', '$2a$12$nH7YfMvw0KUjq3PE9JRUz.FHZrMYnSHQYB0OW8IEGT3qVdxWkECaS'); -- password: a@123
--
-- Assign roles to users
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1); -- customer has role USER
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2); -- admin has role ADMIN
