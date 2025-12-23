-- Products
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(300) NOT NULL,
    slug VARCHAR(300) UNIQUE NOT NULL,
    description TEXT,
    short_description VARCHAR(500),
    brand_id BIGINT REFERENCES brands(id) ON DELETE SET NULL,
    base_price DECIMAL(10, 2) NOT NULL,
    sale_price DECIMAL(10, 2),
    cost_price DECIMAL(10, 2),
    product_type VARCHAR(20) DEFAULT 'SIMPLE' CHECK (product_type IN ('SIMPLE', 'VARIANT', 'DIGITAL')),
    status VARCHAR(20) DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'ACTIVE', 'INACTIVE', 'OUT_OF_STOCK')),
    is_featured BOOLEAN DEFAULT FALSE,
    weight DECIMAL(10, 2),
    length DECIMAL(10, 2),
    width DECIMAL(10, 2),
    height DECIMAL(10, 2),
    meta_title VARCHAR(200),
    meta_description TEXT,
    meta_keywords TEXT,
    view_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_products_sku ON products(sku);
CREATE INDEX idx_products_slug ON products(slug);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_products_is_featured ON products(is_featured);
CREATE INDEX idx_products_search ON products USING gin(to_tsvector('english', name || ' ' || COALESCE(description, '')));

-- Product Categories (many-to-many)
CREATE TABLE product_categories (
    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, category_id)
);
