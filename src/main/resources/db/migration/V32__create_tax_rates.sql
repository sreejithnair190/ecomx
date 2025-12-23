-- Tax Rates
CREATE TABLE tax_rates (
    id BIGSERIAL PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    tax_rate DECIMAL(5, 2) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tax_rates_location ON tax_rates(country, state);
