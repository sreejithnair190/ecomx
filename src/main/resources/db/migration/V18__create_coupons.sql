-- Coupons
CREATE TABLE coupons (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    discount_type VARCHAR(20) NOT NULL CHECK (discount_type IN ('PERCENTAGE', 'FIXED_AMOUNT', 'FREE_SHIPPING')),
    discount_value DECIMAL(10, 2) NOT NULL,
    
    max_uses INT,
    max_uses_per_user INT DEFAULT 1,
    current_uses INT DEFAULT 0,
    
    minimum_order_amount DECIMAL(10, 2),
    maximum_discount_amount DECIMAL(10, 2),
    applicable_products JSONB,
    applicable_categories JSONB,
    
    starts_at TIMESTAMP,
    expires_at TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_coupons_code ON coupons(code);
CREATE INDEX idx_coupons_is_active ON coupons(is_active);
CREATE INDEX idx_coupons_expires_at ON coupons(expires_at);
