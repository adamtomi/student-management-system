-- Create 'student' table
CREATE TABLE IF NOT EXISTS student_management.student (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
