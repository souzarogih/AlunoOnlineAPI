CREATE TABLE IF NOT EXISTS student_enrollment (
    id BIGSERIAL PRIMARY KEY,
    grade1 DOUBLE PRECISION,       -- Nota 1
    grade2 DOUBLE PRECISION,       -- Nota 2
    student_id BIGINT,             -- Relacionamento com a tabela Student
    subject_id BIGINT,             -- Relacionamento com a tabela Course
    status VARCHAR(50),            -- Para armazenar o valor da enum EnrollmentStudentStatusEnum
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE,
    CONSTRAINT fk_subject FOREIGN KEY (subject_id) REFERENCES Course(id) ON DELETE CASCADE
);