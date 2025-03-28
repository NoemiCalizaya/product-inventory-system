CREATE TABLE IF NOT EXISTS purchases (
    purchase_id BIGSERIAL PRIMARY KEY,
    salesman_ci VARCHAR(255),
    supplier_id BIGINT,
    date_acquisition DATE,
    state VARCHAR(255),
    purchase_cost DECIMAL(10,2)
);
