-- Seed default roles
INSERT INTO roles (name, description) VALUES
    ('CUSTOMER', 'Default customer role'),
    ('SUPER_ADMIN', 'Administrator with full access'),
    ('ADMIN', 'Administrator with access'),
    ('VENDOR', 'Vendor role')
ON CONFLICT (name) DO NOTHING;
