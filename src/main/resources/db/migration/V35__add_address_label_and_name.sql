-- Add address label and name columns
ALTER TABLE addresses
    ADD COLUMN address_label VARCHAR(20) DEFAULT 'HOME' CHECK (address_label IN ('HOME', 'WORK', 'OTHER')),
    ADD COLUMN address_name VARCHAR(100);
