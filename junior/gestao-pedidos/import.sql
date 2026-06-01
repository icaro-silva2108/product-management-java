-- ============================================================
-- Gestão de Pedidos — Seed de dados
-- ============================================================

-- Categorias
INSERT INTO categorias (nome) VALUES
('Roupas'),
('Calçados'),
('Acessórios'),
('Eletrônicos'),
('Casa e Decoração');

-- Produtos
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id, ativo) VALUES
('Camiseta Básica Branca', '100% algodão, tamanho M', 49.90, 150, 1, true),
('Camiseta Básica Preta', '100% algodão, tamanho M', 49.90, 120, 1, true),
('Calça Jeans Slim', 'Jeans azul, corte slim', 189.90, 80, 1, true),
('Moletom Cinza', 'Moletom com capuz, tamanho G', 129.90, 60, 1, true),
('Vestido Floral', 'Vestido midi floral, tamanho P', 159.90, 45, 1, true),
('Tênis Casual Branco', 'Solado emborrachado, tamanho 40', 249.90, 70, 2, true),
('Sandália Rasteira Bege', 'Couro sintético, tamanho 37', 89.90, 90, 2, true),
('Bota Couro Marrom', 'Couro legítimo, tamanho 42', 399.90, 30, 2, true),
('Boné Preto', 'Aba reta, tamanho único', 59.90, 200, 3, true),
('Bolsa Transversal Preta', 'Couro sintético, pequena', 149.90, 55, 3, true),
('Relógio Prata', 'Mostrador redondo, pulseira metálica', 299.90, 25, 3, true),
('Fone Bluetooth', 'Over-ear, cancelamento de ruído', 349.90, 40, 4, true),
('Carregador Portátil 10000mAh', 'Entrada USB-C e micro-USB', 129.90, 65, 4, true),
('Luminária de Mesa LED', 'Luz fria e quente, 3 intensidades', 99.90, 50, 5, true),
('Almofada Decorativa', '45x45cm, capa lavável', 69.90, 80, 5, false);

-- Clientes
INSERT INTO clientes (nome, email, telefone, criado_em) VALUES
('Ana Lima', 'ana.lima@email.com', '11999990001', '2026-01-02 09:00:00'),
('Carlos Souza', 'carlos.souza@email.com', '11999990002', '2026-01-03 10:00:00'),
('Fernanda Rocha', 'fernanda.rocha@email.com', '11999990003', '2026-01-05 11:00:00'),
('João Silva', 'joao.silva@email.com', '11999990004', '2026-01-07 14:00:00'),
('Mariana Costa', 'mariana.costa@email.com', '11999990005', '2026-01-10 09:30:00'),
('Pedro Alves', 'pedro.alves@email.com', '11999990006', '2026-01-12 16:00:00'),
('Luciana Martins', 'luciana.martins@email.com', '11999990007', '2026-01-15 10:00:00'),
('Rafael Oliveira', 'rafael.oliveira@email.com', '11999990008', '2026-02-01 09:00:00'),
('Patrícia Santos', 'patricia.santos@email.com', '11999990009', '2026-02-05 11:00:00'),
('Bruno Costa', 'bruno.costa@email.com', '11999990010', '2026-02-10 14:00:00');

-- Pedidos e Itens — Janeiro 2026
INSERT INTO pedidos (cliente_id, status, valor_total, criado_em, atualizado_em) VALUES
(1, 'ENTREGUE', 299.70, '2026-01-05 10:00:00', '2026-01-10 15:00:00'),
(2, 'ENTREGUE', 449.80, '2026-01-06 11:00:00', '2026-01-12 14:00:00'),
(3, 'ENTREGUE', 189.90, '2026-01-08 09:00:00', '2026-01-14 10:00:00'),
(4, 'CANCELADO', 129.90, '2026-01-09 14:00:00', '2026-01-09 16:00:00'),
(1, 'ENTREGUE', 599.80, '2026-01-12 10:00:00', '2026-01-18 15:00:00'),
(5, 'ENTREGUE', 249.90, '2026-01-14 11:00:00', '2026-01-20 14:00:00'),
(2, 'ENTREGUE', 349.90, '2026-01-15 09:00:00', '2026-01-21 10:00:00'),
(6, 'CANCELADO', 189.90, '2026-01-16 14:00:00', '2026-01-16 18:00:00'),
(3, 'ENTREGUE', 459.70, '2026-01-18 10:00:00', '2026-01-24 15:00:00'),
(7, 'ENTREGUE', 299.90, '2026-01-20 11:00:00', '2026-01-26 14:00:00'),
(4, 'ENTREGUE', 179.80, '2026-01-22 09:00:00', '2026-01-28 10:00:00'),
(1, 'ENVIADO', 699.70, '2026-01-25 14:00:00', '2026-01-28 16:00:00'),
(5, 'CONFIRMADO', 259.80, '2026-01-28 10:00:00', '2026-01-28 12:00:00'),
(6, 'PENDENTE', 149.90, '2026-01-30 11:00:00', '2026-01-30 11:00:00');

INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
(1, 3, 1, 189.90), (1, 9, 2, 59.90),
(2, 6, 1, 249.90), (2, 10, 1, 149.90), (2, 1, 1, 49.90),
(3, 3, 1, 189.90),
(4, 4, 1, 129.90),
(5, 12, 1, 349.90), (5, 6, 1, 249.90),
(6, 6, 1, 249.90),
(7, 12, 1, 349.90),
(8, 3, 1, 189.90),
(9, 8, 1, 399.90), (9, 9, 1, 59.90),
(10, 11, 1, 299.90),
(11, 1, 2, 49.90), (11, 9, 1, 59.90), (11, 2, 1, 49.90),
(12, 5, 1, 159.90), (12, 12, 1, 349.90), (12, 13, 1, 129.90),
(13, 1, 2, 49.90), (13, 7, 1, 89.90), (13, 9, 1, 59.90),
(14, 10, 1, 149.90);

-- Pedidos e Itens — Fevereiro 2026
INSERT INTO pedidos (cliente_id, status, valor_total, criado_em, atualizado_em) VALUES
(8, 'ENTREGUE', 399.80, '2026-02-03 10:00:00', '2026-02-09 15:00:00'),
(9, 'ENTREGUE', 279.80, '2026-02-05 11:00:00', '2026-02-11 14:00:00'),
(10, 'ENTREGUE', 549.80, '2026-02-07 09:00:00', '2026-02-13 10:00:00'),
(1, 'CANCELADO', 249.90, '2026-02-08 14:00:00', '2026-02-08 18:00:00'),
(2, 'ENTREGUE', 349.90, '2026-02-10 10:00:00', '2026-02-16 15:00:00'),
(3, 'ENTREGUE', 229.80, '2026-02-12 11:00:00', '2026-02-18 14:00:00'),
(4, 'ENTREGUE', 449.90, '2026-02-14 09:00:00', '2026-02-20 10:00:00'),
(5, 'CANCELADO', 129.90, '2026-02-15 14:00:00', '2026-02-15 17:00:00'),
(6, 'ENTREGUE', 299.80, '2026-02-18 10:00:00', '2026-02-24 15:00:00'),
(7, 'ENTREGUE', 189.90, '2026-02-20 11:00:00', '2026-02-26 14:00:00'),
(8, 'EM_PREPARO', 479.80, '2026-02-22 09:00:00', '2026-02-23 10:00:00'),
(9, 'PENDENTE', 99.90, '2026-02-25 14:00:00', '2026-02-25 14:00:00');

INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
(15, 13, 1, 129.90), (15, 12, 1, 349.90), (15, 9, 2, 59.90),
(16, 7, 1, 89.90), (16, 1, 1, 49.90), (16, 4, 1, 129.90),
(17, 12, 1, 349.90), (17, 13, 1, 129.90), (17, 9, 1, 59.90),
(18, 6, 1, 249.90),
(19, 12, 1, 349.90),
(20, 2, 2, 49.90), (20, 9, 1, 59.90), (20, 7, 1, 89.90),
(21, 8, 1, 399.90), (21, 1, 1, 49.90),
(22, 4, 1, 129.90),
(23, 11, 1, 299.90), (23, 9, 1, 59.90), (23, 7, 1, 89.90),
(24, 3, 1, 189.90),
(25, 6, 1, 249.90), (25, 13, 1, 129.90), (25, 2, 2, 49.90),
(26, 14, 1, 99.90);

-- Pedidos e Itens — Março 2026
INSERT INTO pedidos (cliente_id, status, valor_total, criado_em, atualizado_em) VALUES
(10, 'ENTREGUE', 599.80, '2026-03-02 10:00:00', '2026-03-08 15:00:00'),
(1, 'ENTREGUE', 309.80, '2026-03-04 11:00:00', '2026-03-10 14:00:00'),
(2, 'ENTREGUE', 449.90, '2026-03-06 09:00:00', '2026-03-12 10:00:00'),
(3, 'CANCELADO', 349.90, '2026-03-07 14:00:00', '2026-03-07 17:00:00'),
(4, 'ENTREGUE', 279.80, '2026-03-10 10:00:00', '2026-03-16 15:00:00'),
(5, 'ENTREGUE', 399.90, '2026-03-12 11:00:00', '2026-03-18 14:00:00'),
(6, 'ENTREGUE', 249.80, '2026-03-14 09:00:00', '2026-03-20 10:00:00'),
(7, 'EM_PREPARO', 549.80, '2026-03-16 14:00:00', '2026-03-17 10:00:00'),
(8, 'CONFIRMADO', 189.90, '2026-03-18 10:00:00', '2026-03-18 12:00:00'),
(9, 'PENDENTE', 299.90, '2026-03-20 11:00:00', '2026-03-20 11:00:00'),
(10, 'PENDENTE', 129.90, '2026-03-22 09:00:00', '2026-03-22 09:00:00');

INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
(27, 12, 1, 349.90), (27, 6, 1, 249.90),
(28, 13, 1, 129.90), (28, 9, 1, 59.90), (28, 4, 1, 129.90),
(29, 8, 1, 399.90), (29, 1, 1, 49.90),
(30, 12, 1, 349.90),
(31, 2, 2, 49.90), (31, 7, 1, 89.90), (31, 9, 1, 59.90), (31, 11, 1, 299.90),
(32, 8, 1, 399.90),
(33, 13, 1, 129.90), (33, 4, 1, 129.90),
(34, 12, 1, 349.90), (34, 13, 1, 129.90), (34, 2, 1, 49.90),
(35, 3, 1, 189.90),
(36, 11, 1, 299.90),
(37, 4, 1, 129.90);
