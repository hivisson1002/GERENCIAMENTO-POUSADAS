-- Sequência para identificação de pousada
CREATE SEQUENCE pou_pou_id_seq START WITH 1;

-- Tabela de pessoa
CREATE TABLE pessoa (
    usuario CHAR(20) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    telefone VARCHAR(14),
    CONSTRAINT pk_pessoa PRIMARY KEY (usuario)
);

-- Tabela de pousada
CREATE TABLE pousada (
    pou_id SERIAL PRIMARY KEY,
    pou_nome VARCHAR(50) NOT NULL,
    pou_end VARCHAR(100),
    pou_bairro VARCHAR(50),
    pou_cid VARCHAR(50),
    pou_estado CHAR(2),
    pou_tel VARCHAR(14),
    pou_estrelas INTEGER CHECK (pou_estrelas BETWEEN 1 AND 5),
    pou_vlr_qua1 INTEGER CHECK (pou_vlr_qua1 BETWEEN 0 AND 99999),
    pou_vlr_qua2 INTEGER CHECK (pou_vlr_qua2 BETWEEN 0 AND 99999),
    pou_vlr_qua3 INTEGER CHECK (pou_vlr_qua3 BETWEEN 0 AND 99999),
    pou_obs VARCHAR(200),
    pou_site VARCHAR(100),
    pou_status CHAR(1) DEFAULT 'S' CHECK (pou_status IN ('N', 'S'))
);

-- Tabela de quarto
CREATE TABLE quarto (
    qua_id SERIAL PRIMARY KEY,
    qua_pou INTEGER NOT NULL REFERENCES pousada (pou_id) ON DELETE CASCADE,
    qua_nome VARCHAR(50),
    qua_tipo VARCHAR(50),
    qua_jacuzzi VARCHAR(50),
    qua_sala_de_estar VARCHAR(50),
    qua_piscina_privativa VARCHAR(50),
    qua_camas NUMERIC(2),
    qua_valor_dia NUMERIC(10)
);

-- Tabela de reserva
CREATE TABLE reserva (
    res_id SERIAL PRIMARY KEY,
    res_pou INTEGER NOT NULL REFERENCES pousada (pou_id) ON DELETE CASCADE,
    res_qua INTEGER NOT NULL REFERENCES quarto (qua_id) ON DELETE CASCADE,
    res_pessoa CHAR(20) NOT NULL REFERENCES pessoa (usuario) ON DELETE CASCADE,
    res_data_entrada DATE NOT NULL,
    res_data_saida DATE NOT NULL,
    res_status CHAR(1) DEFAULT 'S'
);

ALTER TABLE reserva ADD CONSTRAINT unique_reserva_pou_qua_data UNIQUE (res_pou, res_qua, res_data_entrada);

-- Tabela de pessoa_fisica
CREATE TABLE pessoa_fisica (
    pf_usuario CHAR(20) NOT NULL PRIMARY KEY,
    pf_nome VARCHAR(50),
    pf_cpf INTEGER,
    pf_sexo CHAR(1),
    CONSTRAINT fk_pf_usuario FOREIGN KEY (pf_usuario) REFERENCES pessoa (usuario)
);

-- Tabela de pessoa_juridica
CREATE TABLE pessoa_juridica (
    pj_usuario CHAR(20) NOT NULL PRIMARY KEY,
    pj_nome VARCHAR(50),
    pj_cnpj INTEGER,
    pj_razao VARCHAR(100),
    CONSTRAINT fk_pj_usuario FOREIGN KEY (pj_usuario) REFERENCES pessoa (usuario)
);

-- Tabela de funcionario
CREATE TABLE funcionario (
    fun_usuario CHAR(20) NOT NULL PRIMARY KEY,
    fun_nome VARCHAR(50),
    fun_cpf INTEGER,
    fun_sexo CHAR(1),
    fun_cargo VARCHAR(50),
    fun_salario integer,
    CONSTRAINT fk_fun_usuario FOREIGN KEY (fun_usuario) REFERENCES pessoa (usuario)
);

-- Função e gatilho para a sequência de pousada
CREATE OR REPLACE FUNCTION pou_pou_id_trg()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.pou_id IS NULL THEN
        NEW.pou_id := nextval('pou_pou_id_seq');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER pou_pou_id_trg BEFORE INSERT ON pousada
FOR EACH ROW EXECUTE FUNCTION pou_pou_id_trg();

-- Sequência para identificação de quarto
CREATE SEQUENCE qua_id_seq START WITH 1;

-- Função e gatilho para a sequência de quarto
CREATE OR REPLACE FUNCTION qua_id_trg()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.qua_id IS NULL THEN
        NEW.qua_id := nextval('qua_id_seq');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER qua_id_trg BEFORE INSERT ON quarto
FOR EACH ROW EXECUTE FUNCTION qua_id_trg();

-- Sequência para identificação de reserva
CREATE SEQUENCE res_res_id_seq START WITH 1;

-- Função e gatilho para a sequência de reserva
CREATE OR REPLACE FUNCTION res_res_id_trg()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.res_id IS NULL THEN
        NEW.res_id := nextval('res_res_id_seq');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER res_res_id_trg BEFORE INSERT ON reserva
FOR EACH ROW EXECUTE FUNCTION res_res_id_trg();
