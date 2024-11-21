CREATE TABLE IF NOT EXISTS invoice (
    id BIGSERIAL PRIMARY KEY,
    student_financial_id BIGINT,  -- Relacionamento com a tabela StudentFinance
    dueDate TIMESTAMP,            -- Data de vencimento
    paidOn TIMESTAMP,             -- Data de pagamento
    createdAt TIMESTAMP,          -- Data de criação
    value DOUBLE PRECISION,       -- Valor da fatura
    CONSTRAINT fk_student_financial FOREIGN KEY (student_financial_id) REFERENCES student_finance(id) ON DELETE CASCADE
);