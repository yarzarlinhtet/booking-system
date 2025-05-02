INSERT INTO users (
    id,
    created_date,
    updated_date,
    name,
    email,
    password,
    is_verified,
    verification_token
) VALUES
('101f1eaa-1b2c-4d3e-9f4a-56789abc0001', NOW(), NOW(), 'Aung Aung', 'aungaung@gmail.com', '$2a$10$XVhrvBO5suar.K0/q7sgPuqPj4sDaZxOi1lVevkl2zP7YyKSWJulq', TRUE, NULL), //password: password
('102f2ebb-2c3d-5e4f-af5b-67890bcd0002', NOW(), NOW(), 'Mya Mya', 'myamya@gmail.com', '$2a$10$XVhrvBO5suar.K0/q7sgPuqPj4sDaZxOi1lVevkl2zP7YyKSWJulq', FALSE, 'token123'),//password: password
('103f3fcc-3d4e-6f5a-bf6c-78901cde0003', NOW(), NOW(), 'John Tan', 'johntan@gmail.com', '$2a$10$XVhrvBO5suar.K0/q7sgPuqPj4sDaZxOi1lVevkl2zP7YyKSWJulq', TRUE, NULL),//password: password
('104f4add-4e5f-7a6b-cf7d-89012def0004', NOW(), NOW(), 'Li Wei', 'liwei@gmail.com', '$2a$10$XVhrvBO5suar.K0/q7sgPuqPj4sDaZxOi1lVevkl2zP7YyKSWJulq', FALSE, 'token456');//password: password
