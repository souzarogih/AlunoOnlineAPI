CREATE TYPE degree_type_enum AS ENUM ('GRADUACAO', 'ESPECIALIZACAO', 'MESTRADO', 'DOUTORADO');  -- Defina os valores do enum conforme sua necessidade.

CREATE TABLE Degree (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        type degree_type_enum NOT NULL,
                        monthlyCost NUMERIC(15, 2) NOT NULL
);