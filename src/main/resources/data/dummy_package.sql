INSERT INTO class_packages (
    id,
    created_date,
    updated_date,
    country_code,
    name,
    description,
    credit_amount,
    price,
    is_active
) VALUES
-- Myanmar Packages
('a1e6d9e2-1c9b-4d55-9a7f-b3a1bdbfac1d', NOW(), NOW(), 'Myanmar', 'Basic Package MM', 'Basic class package for Myanmar users.', 100, 9.99, TRUE),
('b2e6d9e2-2c9b-4d55-9a7f-b3a1bdbfac2e', NOW(), NOW(), 'Myanmar', 'Standard Package MM', 'Standard class package for Myanmar users.', 250, 19.99, TRUE),
('c3e6d9e2-3c9b-4d55-9a7f-b3a1bdbfac3f', NOW(), NOW(), 'Myanmar', 'Premium Package MM', 'Premium class package for Myanmar users.', 500, 29.99, FALSE),

-- Singapore Packages
('d4e6d9e2-4c9b-4d55-9a7f-b3a1bdbfac4a', NOW(), NOW(), 'Singapore', 'Basic Package SG', 'Basic class package for Singapore users.', 100, 12.99, TRUE),
('e5e6d9e2-5c9b-4d55-9a7f-b3a1bdbfac5b', NOW(), NOW(), 'Singapore', 'Standard Package SG', 'Standard class package for Singapore users.', 250, 24.99, TRUE),
('f6e6d9e2-6c9b-4d55-9a7f-b3a1bdbfac6c', NOW(), NOW(), 'Singapore', 'Premium Package SG', 'Premium class package for Singapore users.', 500, 39.99, TRUE);
