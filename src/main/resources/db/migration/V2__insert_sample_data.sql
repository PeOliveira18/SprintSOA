INSERT INTO concessionarias (id, codigo, nome, cidade, estado, ativo) VALUES (1, 'SP001', 'Ford Center Paulista', 'Sao Paulo', 'SP', 1);
INSERT INTO concessionarias (id, codigo, nome, cidade, estado, ativo) VALUES (2, 'RJ001', 'Ford Barra Service', 'Rio de Janeiro', 'RJ', 1);
INSERT INTO concessionarias (id, codigo, nome, cidade, estado, ativo) VALUES (3, 'PR001', 'Ford Curitiba Norte', 'Curitiba', 'PR', 1);

INSERT INTO clientes (id, nome, email, telefone, documento) VALUES (1, 'Mariana Alves', 'mariana.alves@email.com', '11988887777', '12345678901');
INSERT INTO clientes (id, nome, email, telefone, documento) VALUES (2, 'Bruno Costa', 'bruno.costa@email.com', '21977776666', '98765432100');
INSERT INTO clientes (id, nome, email, telefone, documento) VALUES (3, 'Camila Rocha', 'camila.rocha@email.com', '41966665555', '45678912300');

INSERT INTO veiculos (
    id, vin, marca, modelo, ano_fabricacao, data_compra, quilometragem_atual,
    garantia_ativa, cliente_id, concessionaria_venda_id
) VALUES (1, '9BFZH55L9P8123456', 'Ford', 'Ranger', 2023, DATE '2023-05-20', 28000, 1, 1, 1);

INSERT INTO veiculos (
    id, vin, marca, modelo, ano_fabricacao, data_compra, quilometragem_atual,
    garantia_ativa, cliente_id, concessionaria_venda_id
) VALUES (2, '8AFAR23L7N8765432', 'Ford', 'Territory', 2022, DATE '2022-03-15', 42000, 0, 2, 2);

INSERT INTO veiculos (
    id, vin, marca, modelo, ano_fabricacao, data_compra, quilometragem_atual,
    garantia_ativa, cliente_id, concessionaria_venda_id
) VALUES (3, '9BFBR55L6R7654321', 'Ford', 'Maverick', 2024, DATE '2024-08-10', 9500, 1, 3, 3);

INSERT INTO ordens_servico (
    id, veiculo_id, concessionaria_id, tipo_servico, data_servico, quilometragem,
    valor, status, observacao
) VALUES (1, 1, 1, 'REVISAO', DATE '2024-01-18', 14000, 890.00, 'CONCLUIDA', 'Revisao de 12 meses');

INSERT INTO ordens_servico (
    id, veiculo_id, concessionaria_id, tipo_servico, data_servico, quilometragem,
    valor, status, observacao
) VALUES (2, 1, 1, 'TROCA_OLEO', DATE '2024-07-22', 22000, 420.00, 'CONCLUIDA', 'Troca preventiva');

INSERT INTO ordens_servico (
    id, veiculo_id, concessionaria_id, tipo_servico, data_servico, quilometragem,
    valor, status, observacao
) VALUES (3, 3, 3, 'REVISAO', DATE '2025-01-10', 8000, 760.00, 'CONCLUIDA', 'Primeira revisao');

INSERT INTO leads_retencao (
    id, veiculo_id, concessionaria_id, tipo_lead, prioridade, status, motivo,
    previsao_contato, criado_em, atualizado_em
) VALUES (
    1,
    2,
    2,
    'REVISAO_ATRASADA',
    'ALTA',
    'ABERTO',
    'Cliente sem servico registrado na rede oficial ha mais de 180 dias',
    DATE '2026-05-20',
    CURRENT_TIMESTAMP,
    NULL
);
