-- Create 'students' table
CREATE TABLE IF NOT EXISTS student_management.users (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roleId INTEGER NOT NULL
);

-- Create 'courses' table
CREATE TABLE IF NOT EXISTS student_management.courses (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    courseName VARCHAR(255) NOT NULL,
    teacherName VARCHAR(255) NOT NULL
);

-- Create 'enrollments' table
CREATE TABLE IF NOT EXISTS student_management.enrollments (
    userId VARCHAR(36) NOT NULL,
    courseId VARCHAR(36) NOT NULL,
    FOREIGN KEY (userId) REFERENCES student_management.users(id),
    FOREIGN KEY (courseId) REFERENCES student_management.courses(id)
);

-- Populate 'users' table with a default user with admin privileges.
INSERT INTO student_management.users (id, firstName, lastName, email, password, roleId) VALUES (
    'd4185a66-6b14-4371-bee3-8d298bb93557',
    'John',
    'Doe',
    'test@example.com',
    'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', -- SHA-512 hash of 'admin'
    0 -- 0 stands for 'ADMIN'
);
