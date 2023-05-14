-- Insert role ADMIN --
INSERT INTO roles (id,
                   description,
                   name)
values (1,
        'ROLE_ADMIN',
        'ADMIN');

-- Insert role OWNER --
INSERT INTO roles (id,
                   description,
                   name)
values (2,
        'ROLE_OWNER',
        'OWNER');

-- Insert role EMPLOYEE --
INSERT INTO roles (id,
                   description,
                   name)
values (3,
        'ROLE_EMPLOYEE',
        'EMPLOYEE');

-- Insert role CUSTOMER --
INSERT INTO roles (id,
                   description,
                   name)
values (4,
        'ROLE_CUSTOMER',
        'CUSTOMER');

-- Insert user ADMIN --
INSERT INTO users (id,
                   birthdate,
                   document_number,
                   email,
                   first_name,
                   last_name,
                   password,
                   phone_number,
                   id_role)
values (1,
        '2000-01-01',
        '12345678900',
        'admin@gmail.com',
        'adminName',
        'adminSurname',
        '$2a$10$G.2DHaPhPXfVzh/bn71KruzG/13XPjfwP6pRVOjeBGGCAEL0CU51W',
        '+1234567890',
        1);

-- insert user OWNER --
-- This insert is if you want to create the first owner from here,
-- otherwise you can create them from the endpoint --
INSERT INTO users (id,
                   birthdate,
                   document_number,
                   email,
                   first_name,
                   last_name,
                   password,
                   phone_number,
                   id_role)
values (2,
        '2000-01-01',
        '12345678901',
        'owner@gmail.com',
        'ownerName',
        'ownerSurname',
        '$2a$10$VcqR.Aqm/tIIh8l95n0wgeRj.ZKKGVHKtuO6reXPBaDBU97Xl1COe',
        '+12345678901',
        2);

-- Select all user --
SELECT *
FROM users;

-- Select user and role name --
SELECT u.first_name, u.document_number, r.name
FROM users u
INNER JOIN roles r
ON u.id_role = r.id;

-- Select all roles --
SELECT *
FROM roles;