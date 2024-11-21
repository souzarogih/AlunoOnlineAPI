CREATE TABLE Student (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enrollmentId VARCHAR(255) NOT NULL,
    course_id BIGINT,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES Course(id) ON DELETE SET NULL
);