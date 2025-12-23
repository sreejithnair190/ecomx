-- Wishlist Items
CREATE TABLE wishlist_items (
    id BIGSERIAL PRIMARY KEY,
    wishlist_id BIGINT NOT NULL REFERENCES wishlists(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    variant_id BIGINT REFERENCES product_variants(id) ON DELETE CASCADE,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (wishlist_id, product_id, variant_id)
);

CREATE INDEX idx_wishlist_items_wishlist_id ON wishlist_items(wishlist_id);
