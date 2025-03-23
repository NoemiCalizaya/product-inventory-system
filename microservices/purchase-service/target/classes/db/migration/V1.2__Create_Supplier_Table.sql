CREATE TABLE IF NOT EXISTS suppliers (
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50),
    address TEXT,
    state VARCHAR(20) NOT NULL DEFAULT 'ACTIVE'
);
