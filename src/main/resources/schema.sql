-- Deleta tabelas se existirem para permitir reinicialização limpa
DROP TABLE IF EXISTS ALUGUEIS CASCADE;
DROP TABLE IF EXISTS ARMARIOS CASCADE;
DROP TABLE IF EXISTS CLIENTES CASCADE;
DROP TABLE IF EXISTS LOCALIZACOES CASCADE;

-- Tabela de Localizações
CREATE TABLE LOCALIZACOES (
                              id BIGSERIAL PRIMARY KEY,
                              nome VARCHAR(255) NOT NULL,
                              endereco VARCHAR(255),
                              cidade VARCHAR(100),
                              descricao VARCHAR(500)
);

-- Tabela de Clientes
CREATE TABLE CLIENTES (
                          id BIGSERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          telefone VARCHAR(20)
);

-- Tabela de Armários
CREATE TABLE ARMARIOS (
                          id BIGSERIAL PRIMARY KEY,
                          numero_armario VARCHAR(50) NOT NULL,
                          tamanho VARCHAR(20) NOT NULL,
                          status VARCHAR(20) NOT NULL,
                          localizacao_id BIGINT,
                          observacoes VARCHAR(500),
                          CONSTRAINT fk_localizacao FOREIGN KEY (localizacao_id) REFERENCES LOCALIZACOES(id) ON DELETE SET NULL,
                          UNIQUE (localizacao_id, numero_armario)
);

-- Tabela de Aluguéis
CREATE TABLE ALUGUEIS (
                          id BIGSERIAL PRIMARY KEY,
                          cliente_id BIGINT NOT NULL,
                          armario_id BIGINT NOT NULL,
                          data_hora_inicio TIMESTAMP NOT NULL,
                          data_hora_fim TIMESTAMP,
                          codigo_acesso VARCHAR(50) NOT NULL UNIQUE,
                          status VARCHAR(25) NOT NULL,
                          preco DECIMAL(10, 2),
                          data_hora_pagamento TIMESTAMP,
                          detalhes_pagamento VARCHAR(255),
                          CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES CLIENTES(id),
                          CONSTRAINT fk_armario FOREIGN KEY (armario_id) REFERENCES ARMARIOS(id)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_armario_status ON ARMARIOS(status);
CREATE INDEX IF NOT EXISTS idx_armario_localizacao_id ON ARMARIOS(localizacao_id);
CREATE INDEX IF NOT EXISTS idx_aluguel_cliente_id ON ALUGUEIS(cliente_id);
CREATE INDEX IF NOT EXISTS idx_aluguel_armario_id ON ALUGUEIS(armario_id);
CREATE INDEX IF NOT EXISTS idx_aluguel_status ON ALUGUEIS(status);