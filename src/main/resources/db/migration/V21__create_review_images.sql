-- Review Images
CREATE TABLE review_images (
    id BIGSERIAL PRIMARY KEY,
    review_id BIGINT NOT NULL REFERENCES product_reviews(id) ON DELETE CASCADE,
    image_url VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_review_images_review_id ON review_images(review_id);
