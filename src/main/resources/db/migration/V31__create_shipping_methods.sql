-- Shipping Methods
CREATE TABLE shipping_methods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    cost DECIMAL(10, 2) NOT NULL,
    estimated_days INT,
    is_active BOOLEAN DEFAULT TRUE,
    min_order_amount DECIMAL(10, 2) DEFAULT 0,
    max_order_amount DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_shipping_methods_is_active ON shipping_methods(is_active);
