-- Attributes (for variants like Size, Color)
CREATE TABLE attributes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Attribute Values
CREATE TABLE attribute_values (
    id BIGSERIAL PRIMARY KEY,
    attribute_id BIGINT NOT NULL REFERENCES attributes(id) ON DELETE CASCADE,
    value VARCHAR(100) NOT NULL,
    display_value VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_attribute_values_attribute_id ON attribute_values(attribute_id);
