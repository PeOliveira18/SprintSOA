CREATE SEQUENCE SEQ_CONCESSIONARIAS START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE SEQ_CLIENTES START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE SEQ_VEICULOS START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE SEQ_ORDENS_SERVICO START WITH 100 INCREMENT BY 1;
CREATE SEQUENCE SEQ_LEADS_RETENCAO START WITH 100 INCREMENT BY 1;

CREATE TABLE concessionarias (
    id NUMBER(19) NOT NULL,
    codigo VARCHAR2(20) NOT NULL,
    nome VARCHAR2(120) NOT NULL,
    cidade VARCHAR2(80) NOT NULL,
    estado VARCHAR2(2) NOT NULL,
    ativo NUMBER(1) DEFAULT 1 NOT NULL,
    CONSTRAINT pk_concessionarias PRIMARY KEY (id),
    CONSTRAINT uk_concessionarias_codigo UNIQUE (codigo),
    CONSTRAINT ck_concessionarias_ativo CHECK (ativo IN (0, 1))
);

CREATE TABLE clientes (
    id NUMBER(19) NOT NULL,
    nome VARCHAR2(120) NOT NULL,
    email VARCHAR2(120) NOT NULL,
    telefone VARCHAR2(20) NOT NULL,
    documento VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_clientes PRIMARY KEY (id),
    CONSTRAINT uk_clientes_email UNIQUE (email)
);

CREATE TABLE veiculos (
    id NUMBER(19) NOT NULL,
    vin VARCHAR2(17) NOT NULL,
    marca VARCHAR2(40) NOT NULL,
    modelo VARCHAR2(80) NOT NULL,
    ano_fabricacao NUMBER(4) NOT NULL,
    data_compra DATE NOT NULL,
    quilometragem_atual NUMBER(10) NOT NULL,
    garantia_ativa NUMBER(1) NOT NULL,
    cliente_id NUMBER(19) NOT NULL,
    concessionaria_venda_id NUMBER(19) NOT NULL,
    CONSTRAINT pk_veiculos PRIMARY KEY (id),
    CONSTRAINT uk_veiculos_vin UNIQUE (vin),
    CONSTRAINT ck_veiculos_garantia CHECK (garantia_ativa IN (0, 1)),
    CONSTRAINT fk_veiculos_clientes FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_veiculos_concessionarias FOREIGN KEY (concessionaria_venda_id) REFERENCES concessionarias(id)
);

CREATE TABLE ordens_servico (
    id NUMBER(19) NOT NULL,
    veiculo_id NUMBER(19) NOT NULL,
    concessionaria_id NUMBER(19) NOT NULL,
    tipo_servico VARCHAR2(40) NOT NULL,
    data_servico DATE NOT NULL,
    quilometragem NUMBER(10) NOT NULL,
    valor NUMBER(12,2) NOT NULL,
    status VARCHAR2(30) NOT NULL,
    observacao VARCHAR2(255),
    CONSTRAINT pk_ordens_servico PRIMARY KEY (id),
    CONSTRAINT fk_ordens_veiculos FOREIGN KEY (veiculo_id) REFERENCES veiculos(id),
    CONSTRAINT fk_ordens_concessionarias FOREIGN KEY (concessionaria_id) REFERENCES concessionarias(id)
);

CREATE TABLE leads_retencao (
    id NUMBER(19) NOT NULL,
    veiculo_id NUMBER(19) NOT NULL,
    concessionaria_id NUMBER(19) NOT NULL,
    tipo_lead VARCHAR2(40) NOT NULL,
    prioridade VARCHAR2(20) NOT NULL,
    status VARCHAR2(30) NOT NULL,
    motivo VARCHAR2(255) NOT NULL,
    previsao_contato DATE NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    atualizado_em TIMESTAMP,
    CONSTRAINT pk_leads_retencao PRIMARY KEY (id),
    CONSTRAINT fk_leads_veiculos FOREIGN KEY (veiculo_id) REFERENCES veiculos(id),
    CONSTRAINT fk_leads_concessionarias FOREIGN KEY (concessionaria_id) REFERENCES concessionarias(id)
);
