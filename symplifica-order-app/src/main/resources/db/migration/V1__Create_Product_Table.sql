CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price VARCHAR(100) NOT NULL,
    current_stock INT NOT NULL
);
