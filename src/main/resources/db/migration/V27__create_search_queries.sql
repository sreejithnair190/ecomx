-- Search Queries
CREATE TABLE search_queries (
    id BIGSERIAL PRIMARY KEY,
    query VARCHAR(500) NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    session_id VARCHAR(255),
    results_count INT,
    searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_search_queries_query ON search_queries(query);
CREATE INDEX idx_search_queries_searched_at ON search_queries(searched_at);
