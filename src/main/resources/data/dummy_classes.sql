INSERT INTO classes (
    id,
    created_date,
    updated_date,
    country_code,
    name,
    description,
    start_at,
    end_at,
    max_capacity,
    credit_amount
) VALUES
-- Myanmar Classes
('a3f5d1e8-10b9-4c1d-8d3a-47f1e9f00101', NOW(), NOW(), 'Myanmar', 'Beginner Class MM', 'Introductory class for Myanmar students.', NOW() + INTERVAL '1 day', NOW() + INTERVAL '1 day 1 hour', 2, 50),
('b4f6d2e9-21ca-4d2e-9e4b-58f2f0f00202', NOW(), NOW(), 'Myanmar', 'Intermediate Class MM', 'Intermediate class for Myanmar students.', NOW() + INTERVAL '1 day', NOW() + INTERVAL '1 day 1 hour', 3, 100),
('c5f7e3fa-32db-4e3f-af5c-69f3f1f00303', NOW(), NOW(), 'Myanmar', 'Advanced Class MM', 'Advanced class for Myanmar students.', NOW() + INTERVAL '1 day', NOW() + INTERVAL '1 day 1 hour', 1, 150),

-- Singapore Classes
('d6f8f4fb-43ec-4f40-bf6d-70f4f2f00404', NOW(), NOW(), 'Singapore', 'Beginner Class SG', 'Introductory class for Singapore students.', NOW() + INTERVAL '1 day', NOW() + INTERVAL '1 day 1 hour', 1, 50),
('e7f9a5fc-54fd-4a51-af7e-81f5a3a00505', NOW(), NOW(), 'Singapore', 'Intermediate Class SG', 'Intermediate class for Singapore students.', NOW() + INTERVAL '1 day', NOW() + INTERVAL '1 day 1 hour', 3, 100),
('f8a0b6fd-65ae-4b62-bf8f-92a6b4b00606', NOW(), NOW(), 'Singapore', 'Advanced Class SG', 'Advanced class for Singapore students.', NOW() + INTERVAL '1 day', NOW() + INTERVAL '1 day 1 hour', 4, 150);
