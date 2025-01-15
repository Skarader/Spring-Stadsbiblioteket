-- Create tables if they don't exist
CREATE TABLE IF NOT EXISTS authors (
    author_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE
);
CREATE TABLE IF NOT EXISTS books (
    book_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    publication_year INT,
    author_id BIGINT,
    available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id)
);
CREATE TABLE IF NOT EXISTS genres (
    genre_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS books_genres (
    book_id BIGINT,
    genre_id BIGINT,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    member_number VARCHAR(10) NOT NULL UNIQUE,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);
CREATE TABLE IF NOT EXISTS loans (
    loan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id BIGINT,
    user_id BIGINT,
    loan_date DATE NOT NULL,
    due_date DATE NOT NULL,
    returned_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
CREATE TABLE IF NOT EXISTS admins (
    admin_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    role VARCHAR(20) NOT NULL
);
-- Clear existing data
DELETE FROM books_genres;
DELETE FROM genres;
DELETE FROM loans;
DELETE FROM books;
DELETE FROM authors;
DELETE FROM users;
DELETE FROM admins;
-- Populate authors
INSERT INTO authors (first_name, last_name, birth_date)
VALUES ('Astrid', 'Lindgren', '1907-11-14'),
    ('J.K.', 'Rowling', '1965-07-31'),
    ('Stephen', 'King', '1947-09-21'),
    ('Stieg', 'Larsson', '1954-08-15'),
    ('Virginia', 'Woolf', '1882-01-25');
-- Populate books
INSERT INTO books (
        title,
        publication_year,
        author_id,
        available
    )
VALUES ('Pippi Långstrump', 1945, 1, true),
    ('Bröderna Lejonhjärta', 1973, 1, false),
    (
        'Harry Potter and the Philosopher''s Stone',
        1997,
        2,
        true
    ),
    (
        'Harry Potter and the Chamber of Secrets',
        1998,
        2,
        true
    ),
    (
        'Harry Potter and the Prisoner of Azkaban',
        1999,
        2,
        false
    ),
    ('The Shining', 1977, 3, true),
    ('Pet Sematary', 1983, 3, true),
    ('Män som hatar kvinnor', 2005, 4, true),
    ('Flickan som lekte med elden', 2006, 4, false),
    ('Mrs. Dalloway', 1925, 5, true);
-- Populate genres
INSERT INTO genres (name)
VALUES ('Fantasy'),
    ('Horror'),
    ('Crime'),
    ('Classic'),
    ('Children');
-- Connect books with genres (many-to-many)
INSERT INTO books_genres (book_id, genre_id)
VALUES (1, 5),
    -- Pippi: Children
    (1, 1),
    -- Pippi: Fantasy
    (2, 5),
    -- Bröderna Lejonhjärta: Children
    (2, 1),
    -- Bröderna Lejonhjärta: Fantasy
    (3, 1),
    -- Harry Potter 1: Fantasy
    (4, 1),
    -- Harry Potter 2: Fantasy
    (5, 1),
    -- Harry Potter 3: Fantasy
    (6, 2),
    -- The Shining: Horror
    (7, 2),
    -- Pet Sematary: Horror
    (8, 3),
    -- Män som hatar kvinnor: Crime
    (9, 3),
    -- Flickan som lekte med elden: Crime
    (10, 4);
-- Mrs. Dalloway: Classic
-- Populate users
INSERT INTO users (
        first_name,
        last_name,
        email,
        member_number,
        username,
        password,
        role
    )
VALUES (
        'Lukas',
        'Holm-Wolf',
        'lukas.holmwolf@email.com',
        'MN0001',
        'lukas',
        '$2a$10$thGsg1wtIgn4eVHQsWhPK.9DFnpF4dNIPgBSWTsgcF48R0/Rx/XK2',
        'USER'
    ),
    (
        'Valon',
        'Ahmeti',
        'valon.ahmeti@email.com',
        'MN0002',
        'valon',
        '$2a$10$vPOC6Tvrk1vycf3fVzNKc.WReDrknrlSMSfxozTgo40ivHKSUf.ea',
        'USER'
    ),
    (
        'Eddie',
        'Möllerström',
        'eddie.mollerstrom@email.com',
        'MN0003',
        'eddie',
        '$2a$10$VS7VVEDGoUKm6BeK0TVFV.GJmzvD.oHDVCEFjcSAHWJwv4ix759SC',
        'USER'
    ),
    (
        'Amelia',
        'Matthiesen',
        'amelia.matthiesen@email.com',
        'MN0004',
        'amelia',
        '$2a$10$llY0Wc.nU7dIGJols1cIDum5RQzb7l4Z9zi86wFkwW6ShrWe3HVyS',
        'USER'
    );
-- Populate loans
INSERT INTO loans (
        book_id,
        user_id,
        loan_date,
        due_date,
        returned_date
    )
VALUES (2, 1, '2024-01-15', '2024-02-15', NULL),
    (5, 2, '2024-01-20', '2024-02-20', NULL),
    (9, 3, '2024-01-25', '2024-02-25', NULL),
    (1, 4, '2023-12-15', '2024-01-15', '2024-01-14'),
    (3, 4, '2023-12-20', '2024-01-20', '2024-01-18'),
    (6, 1, '2023-12-25', '2024-01-25', '2024-01-23');
-- Populate admins
INSERT INTO admins (username, password, role)
VALUES (
        'admin',
        '$2a$10$Ki7lPZHiWobCj5o/HD7Ikew0PFS6A/lsPKKcXhbzElMa93vEDXexW',
        'ADMIN'
    )