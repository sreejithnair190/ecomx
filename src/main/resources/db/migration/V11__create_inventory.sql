-- Inventory
CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    variant_id BIGINT REFERENCES product_variants(id) ON DELETE CASCADE,
    warehouse_id BIGINT REFERENCES warehouses(id) ON DELETE CASCADE,
    quantity INT DEFAULT 0,
    reserved_quantity INT DEFAULT 0,
    low_stock_threshold INT DEFAULT 10,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (product_id, variant_id, warehouse_id)
);

CREATE INDEX idx_inventory_product_id ON inventory(product_id);
CREATE INDEX idx_inventory_variant_id ON inventory(variant_id);

-- Inventory Transactions (audit trail)
CREATE TABLE inventory_transactions (
    id BIGSERIAL PRIMARY KEY,
    inventory_id BIGINT NOT NULL REFERENCES inventory(id) ON DELETE CASCADE,
    transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('PURCHASE', 'SALE', 'RETURN', 'ADJUSTMENT', 'RESERVATION', 'RELEASE')),
    quantity INT NOT NULL,
    reference_id BIGINT,
    reference_type VARCHAR(50),
    notes TEXT,
    created_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_inventory_transactions_inventory_id ON inventory_transactions(inventory_id);
CREATE INDEX idx_inventory_transactions_reference ON inventory_transactions(reference_type, reference_id);
