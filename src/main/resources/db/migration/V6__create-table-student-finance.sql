CREATE TABLE student_finance (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT,
    discount DOUBLE PRECISION,
    dueDate INTEGER,
    status VARCHAR(50),
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE
);