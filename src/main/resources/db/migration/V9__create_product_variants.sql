-- Product Variants
CREATE TABLE product_variants (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    sku VARCHAR(100) UNIQUE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    sale_price DECIMAL(10, 2),
    stock_quantity INT DEFAULT 0,
    image_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_variants_product_id ON product_variants(product_id);
CREATE INDEX idx_product_variants_sku ON product_variants(sku);

-- Variant Attribute Values
CREATE TABLE variant_attribute_values (
    variant_id BIGINT REFERENCES product_variants(id) ON DELETE CASCADE,
    attribute_value_id BIGINT REFERENCES attribute_values(id) ON DELETE CASCADE,
    PRIMARY KEY (variant_id, attribute_value_id)
);
