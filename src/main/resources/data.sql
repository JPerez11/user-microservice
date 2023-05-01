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
        '2001-03-01',
        '1007969718',
        'jperez@gmail.com',
        'jhoan',
        'perez',
        '$2a$10$GlsGSNhkbVon6ZOSNMptOu5RikedRzlCAhMa7YpwvUSS0c88WT99S',
        '3164547947',
        1);

INSERT INTO roles (id,
                   description,
                   name)
values (1,
        'ROLE_ADMIN',
        'ADMIN');

INSERT INTO roles (id,
                   description,
                   name)
values (2,
        'ROLE_OWNER',
        'OWNER');

SELECT *
FROM users;

SELECT u.first_name, u.document_number, r.name
FROM users u
INNER JOIN roles r
ON u.id_role = r.id;

SELECT *
FROM roles;