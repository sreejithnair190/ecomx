-- Banners
CREATE TABLE banners (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    link_url VARCHAR(500),
    position VARCHAR(50),
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    starts_at TIMESTAMP,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_banners_position ON banners(position);
CREATE INDEX idx_banners_is_active ON banners(is_active);
