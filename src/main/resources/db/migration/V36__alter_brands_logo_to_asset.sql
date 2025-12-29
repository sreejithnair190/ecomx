-- Replace logo_url with asset_id reference
ALTER TABLE brands DROP COLUMN IF EXISTS logo_url;
ALTER TABLE brands ADD COLUMN asset_id BIGINT REFERENCES assets(id) ON DELETE SET NULL;
