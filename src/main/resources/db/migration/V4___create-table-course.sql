CREATE TABLE Course (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    teacher_id BIGINT,  -- Relacionamento com a tabela Teacher
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id) REFERENCES Teacher(id) ON DELETE SET NULL
);