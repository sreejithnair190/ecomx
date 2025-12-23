-- Page Views
CREATE TABLE page_views (
    id BIGSERIAL PRIMARY KEY,
    page_type VARCHAR(50) NOT NULL,
    page_id BIGINT,
    user_id BIGINT,
    session_id VARCHAR(255),
    ip_address VARCHAR(45),
    user_agent TEXT,
    referer VARCHAR(500),
    viewed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_page_views_page ON page_views(page_type, page_id);
CREATE INDEX idx_page_views_viewed_at ON page_views(viewed_at);
