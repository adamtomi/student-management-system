-- Create 'students' table
CREATE TABLE IF NOT EXISTS student_management.users (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roleId INTEGER NOT NULL,
    UNIQUE (email)
);

-- Create 'courses' table
CREATE TABLE IF NOT EXISTS student_management.courses (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    courseName VARCHAR(255) NOT NULL,
    teacherName VARCHAR(255) NOT NULL,
    UNIQUE (courseName)
);

-- Populate 'users' table with a default user with admin privileges.
INSERT INTO student_management.users (firstName, lastName, email, password, roleId) VALUES (
    'John',
    'Doe',
    'test@example.com',
    'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', -- SHA-512 hash of 'admin'
    0 -- 0 stands for 'ADMIN'
);

INSERT INTO student_management.courses (courseName, teacherName) VALUES (
    'Test course 1',
    'John Doe'
);
