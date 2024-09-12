-- Table for storing customer information
CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(500),
    phone_number VARCHAR(15)
);
-- Table for storing product information
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL CHECK (price >= 0), -- Ensure price is non-negative
    stock_quantity INT DEFAULT 0 CHECK (stock_quantity >= 0) -- Ensure stock is non-negative
);
-- Table for storing order information
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL CHECK (status IN ('PENDING', 'PAID', 'CANCELLED')),
    total_amount DOUBLE NOT NULL CHECK (total_amount >= 0), -- Ensure total amount is non-negative
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE, -- Cascade delete to remove orders if customer is deleted
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE RESTRICT -- Restrict deletion of products that are linked to orders
);

-- Table for storing user information (for authentication)
CREATE TABLE IF NOT EXISTS `users` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
--    email VARCHAR(255) NOT NULL UNIQUE,
--    first_name VARCHAR(255),
--    last_name VARCHAR(255),
--    enabled BOOLEAN DEFAULT TRUE
);

-- Table for storing role information
CREATE TABLE IF NOT EXISTS role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Table for storing user-role mappings
CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);
