-- Dados iniciais para a tabela LOCALIZACOES

INSERT INTO LOCALIZACOES (nome, endereco, cidade, descricao) VALUES
                                                                 ('Aeroporto GRU - Terminal 2 - Desembarque Leste', 'Rodovia Hélio Smidt, s/nº - Cumbica', 'Guarulhos', 'Próximo às esteiras de bagagem 5-7, piso de desembarque.'), -- ID presumido: 1
                                                                 ('Estação Metrô Sé - Linha Vermelha', 'Praça da Sé, s/n - Centro Histórico', 'São Paulo', 'Acesso pela plataforma sentido Palmeiras-Barra Funda, ao lado do Posto de Atendimento ao Usuário.'), -- ID presumido: 2
                                                                 ('Shopping Center Norte - Acesso Principal', 'Tv. Casalbuono, 120 - Vila Guilherme', 'São Paulo', 'Entrada principal do shopping, próximo ao Balcão de Informações e escadas rolantes.'), -- ID presumido: 3
                                                                 ('Rodoviária Tietê - Plataforma 25', 'Av. Cruzeiro do Sul, 1800 - Santana', 'São Paulo', 'Setor de desembarque interestadual, próximo ao guichê de informações rápidas.'), -- ID presumido: 4
                                                                 ('Universidade XYZ - Bloco A (Entrada Biblioteca)', 'Rua do Saber, 123 - Campus Universitário', 'Campinas', 'Entrada principal da Biblioteca Central, ao lado da secretaria do Bloco A.'); -- ID presumido: 5

-- Dados iniciais para a tabela ARMARIOS

INSERT INTO ARMARIOS (numero_armario, tamanho, status, localizacao_id, observacoes) VALUES
                                                                                        ('GRU-T2-A01', 'PEQUENO', 'DISPONIVEL', 1, 'Próximo ao portão D'),
                                                                                        ('GRU-T2-A02', 'MEDIO', 'OCUPADO', 1, NULL),
                                                                                        ('GRU-T2-A03', 'GRANDE', 'MANUTENCAO', 1, 'Fechadura com defeito, aguardando reparo.'),
                                                                                        ('GRU-T2-B01', 'MEDIO', 'DISPONIVEL', 1, 'Corredor lateral esquerdo');

INSERT INTO ARMARIOS (numero_armario, tamanho, status, localizacao_id, observacoes) VALUES
                                                                                        ('SE-LV-M01', 'MEDIO', 'DISPONIVEL', 2, 'Perto da escada rolante norte.'),
                                                                                        ('SE-LV-M02', 'MEDIO', 'DISPONIVEL', 2, NULL),
                                                                                        ('SE-LV-P01', 'PEQUENO', 'OCUPADO', 2, 'Cliente reportou dificuldade ao fechar.');

INSERT INTO ARMARIOS (numero_armario, tamanho, status, localizacao_id, observacoes) VALUES
                                                                                        ('SCN-P01', 'PEQUENO', 'DISPONIVEL', 3, 'Ao lado do fraldário.'),
                                                                                        ('SCN-G01', 'GRANDE', 'FORA_DE_SERVICO', 3, 'Porta danificada.'),
                                                                                        ('SCN-M01', 'MEDIO', 'DISPONIVEL', 3, NULL);

INSERT INTO ARMARIOS (numero_armario, tamanho, status, localizacao_id, observacoes) VALUES
                                                                                        ('RT-P25-A', 'GRANDE', 'DISPONIVEL', 4, NULL),
                                                                                        ('RT-P25-B', 'GRANDE', 'OCUPADO', 4, NULL);

INSERT INTO ARMARIOS (numero_armario, tamanho, status, localizacao_id, observacoes) VALUES
                                                                                        ('UNIXYZ-BA-01', 'PEQUENO', 'DISPONIVEL', 5, 'Para notebooks e mochilas pequenas.'),
                                                                                        ('UNIXYZ-BA-02', 'PEQUENO', 'DISPONIVEL', 5, 'Para notebooks e mochilas pequenas.'),
                                                                                        ('UNIXYZ-BA-03', 'MEDIO', 'MANUTENCAO', 5, 'Sistema de trava eletrônica em revisão.');

-- Dados iniciais para a tabela CLIENTES
INSERT INTO CLIENTES (nome, email, telefone) VALUES
                                                 ('Ana Paula', 'ana.paula@example.com', '(11) 91111-1111'),
                                                 ('Bruno Costa', 'bruno.costa@example.com', '(21) 92222-2222'),
                                                 ('Carla Dias', 'carla.dias@example.com', NULL),
                                                 ('Daniel Farias', 'daniel.farias@example.com', '(31) 93333-3333');

-- Dados iniciais para a tabela ALUGUEIS

INSERT INTO ALUGUEIS (cliente_id, armario_id, data_hora_inicio, codigo_acesso, status, preco) VALUES
    (2, 2, NOW() - INTERVAL '3 hour', 'AB12C3', 'ATIVO', 0.00);

INSERT INTO ALUGUEIS (cliente_id, armario_id, data_hora_inicio, codigo_acesso, status, preco) VALUES
    (3, 7, NOW() - INTERVAL '1 day', 'FG56H7', 'ATIVO', 0.00);

INSERT INTO ALUGUEIS (cliente_id, armario_id, data_hora_inicio, data_hora_fim, codigo_acesso, status, preco) VALUES
    (1, 6, NOW() - INTERVAL '2 day', NOW() - INTERVAL '1 day', 'JK89L0', 'CONCLUIDO', 25.00);

INSERT INTO ALUGUEIS (cliente_id, armario_id, data_hora_inicio, codigo_acesso, status, preco) VALUES
    (4, 12, NOW() - INTERVAL '8 hour', 'MN23P4', 'ATIVO', 0.00);