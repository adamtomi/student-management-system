-- Create 'student' table
CREATE TABLE IF NOT EXISTS student_management.student (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Create 'course' table
CREATE TABLE IF NOT EXISTS student_management.course (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    teacherName VARCHAR(255) NOT NULL
);
