-- Payments
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('CARD', 'UPI', 'NET_BANKING', 'WALLET', 'COD')),
    payment_gateway VARCHAR(50),
    
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'INR',
    
    status VARCHAR(20) DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'REFUNDED')),
    
    transaction_id VARCHAR(255),
    gateway_response JSONB,
    
    card_last4 VARCHAR(4),
    card_brand VARCHAR(20),
    
    failure_reason TEXT,
    refund_amount DECIMAL(10, 2) DEFAULT 0,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP
);

CREATE INDEX idx_payments_order_id ON payments(order_id);
CREATE INDEX idx_payments_transaction_id ON payments(transaction_id);
CREATE INDEX idx_payments_status ON payments(status);
