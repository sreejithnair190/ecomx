-- Remove old image_url column
ALTER TABLE categories DROP COLUMN IF EXISTS image_url;

-- Add asset references
ALTER TABLE categories ADD COLUMN cover_id BIGINT REFERENCES assets(id);
ALTER TABLE categories ADD COLUMN og_image_id BIGINT REFERENCES assets(id);

-- Add new SEO fields (meta_title and meta_description already exist)
ALTER TABLE categories ADD COLUMN meta_keywords VARCHAR(500);
ALTER TABLE categories ADD COLUMN canonical_url VARCHAR(500);
ALTER TABLE categories ADD COLUMN og_title VARCHAR(200);
ALTER TABLE categories ADD COLUMN og_description VARCHAR(500);
ALTER TABLE categories ADD COLUMN robots VARCHAR(100);
ALTER TABLE categories ADD COLUMN structured_data TEXT;

-- Create indexes for asset lookups
CREATE INDEX idx_categories_cover_id ON categories(cover_id);
CREATE INDEX idx_categories_og_image_id ON categories(og_image_id);
