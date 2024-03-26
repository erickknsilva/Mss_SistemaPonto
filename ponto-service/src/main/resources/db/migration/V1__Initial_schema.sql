-- Tabela Funcionario
CREATE TABLE IF NOT EXISTS funcionario (
    matricula INTEGER PRIMARY KEY,
    email VARCHAR(80) NOT NULL,
    departamento VARCHAR(60) NOT NULL,
    salario NUMERIC(6,2) NOT NULL,
    carga_diaria TIME,
    carga_semanal INTEGER,
    carga_mensal INTEGER,
    tipo_contrato VARCHAR(10) NOT NULL,
    created_date timestamp NOT NULL,
    last_modified_date timestamp NOT NULL,
    version integer NOT NULL,
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT matricula_unique UNIQUE (matricula)
);

-- Tabela Ponto
CREATE TABLE IF NOT EXISTS pontos (
    id SERIAL PRIMARY KEY,
    horaEntrada TIME,
    horaSaida TIME,
    horasTrabalhada TIME,
    diaTrabalhado BOOLEAN DEFAULT false,
    data DATE,
    emailFuncionario VARCHAR(85) NOT NULL,
    salarioDia NUMERIC(8,2),
    salarioMes NUMERIC(8,2),
    funcionario INTEGER REFERENCES funcionario(matricula),
    tipocontrato VARCHAR(10) NOT NULL,
    cargadiaria TIME
);