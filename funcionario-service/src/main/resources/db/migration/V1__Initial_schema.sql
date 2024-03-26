
CREATE TABLE IF NOT EXISTS departamentos (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(60) NOT NULL,
    telefone VARCHAR(14),
    responsavel VARCHAR(80),
    created_date timestamp NOT NULL,
    last_modified_date timestamp NOT NULL,
    version integer NOT NULL,
    CONSTRAINT email_unique UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS funcionarios (
    matricula BIGSERIAL NOT NULL PRIMARY KEY,
    nome VARCHAR(30) NOT NULL,
    sobrenome VARCHAR(60) NOT NULL,
    email VARCHAR(80) NOT NULL,
    cargo VARCHAR(80) NOT NULL,
    salario NUMERIC(6, 2) NOT NULL,
    carga_diaria TIME ,
    carga_semanal INTEGER,
    carga_mensal INTEGER,
    tipo_contrato VARCHAR(10) NOT NULL,
    data_entrada DATE,
    departamento_id BIGINT ,
    created_date timestamp NOT NULL,
    last_modified_date timestamp NOT NULL,
    version integer NOT NULL,
    FOREIGN KEY (departamento_id) REFERENCES departamentos(id)
);



