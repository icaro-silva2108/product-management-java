# 🟡 Desafio Pleno: Gestão de Pedidos

**Nível:** Junior++ | **Tema:** CRUD + Relacionamentos + Relatórios | **Tempo estimado:** 5 a 7 dias

Você já sabe fazer CRUD. Agora o jogo muda. Este desafio testa sua capacidade de modelar um sistema com múltiplas entidades relacionadas, aplicar regras de negócio em transições de estado e extrair informações consolidadas do banco usando agregações SQL.

---

## 📚 O que você vai construir

Uma loja online precisa de uma API para gerenciar seus pedidos. O sistema deve permitir o cadastro de clientes, produtos organizados por categorias, e o registro de pedidos com múltiplos itens. Além disso, o time de negócio precisa de relatórios para acompanhar a performance das vendas.

---

## 🗂️ Modelo de Dados

Antes de escrever qualquer código, modele as relações entre as entidades. Entender os relacionamentos é fundamental para construir as queries de relatório corretamente.

**clientes**

| Campo | Tipo | Obrigatório | Regra |
|---|---|---|---|
| `id` | inteiro | gerado auto | — |
| `nome` | texto | ✅ | Mínimo 2 caracteres |
| `email` | texto | ✅ | Formato válido, único no banco |
| `telefone` | texto | ✅ | Formato válido |
| `criado_em` | data/hora | gerado auto | — |

**categorias**

| Campo | Tipo | Obrigatório | Regra |
|---|---|---|---|
| `id` | inteiro | gerado auto | — |
| `nome` | texto | ✅ | Único no banco |

**produtos**

| Campo | Tipo | Obrigatório | Regra |
|---|---|---|---|
| `id` | inteiro | gerado auto | — |
| `nome` | texto | ✅ | Único no banco |
| `descricao` | texto | ❌ | — |
| `preco` | decimal | ✅ | Maior que zero |
| `estoque` | inteiro | ✅ | Não pode ser negativo |
| `categoria_id` | inteiro | ✅ | Deve existir no banco |
| `ativo` | booleano | — | Padrão `true` |

**pedidos**

| Campo | Tipo | Obrigatório | Regra |
|---|---|---|---|
| `id` | inteiro | gerado auto | — |
| `cliente_id` | inteiro | ✅ | Deve existir no banco |
| `status` | enum | — | Padrão `PENDENTE` |
| `valor_total` | decimal | gerado auto | Calculado a partir dos itens |
| `criado_em` | data/hora | gerado auto | — |
| `atualizado_em` | data/hora | gerado auto | — |

**itens_pedido**

| Campo | Tipo | Obrigatório | Regra |
|---|---|---|---|
| `id` | inteiro | gerado auto | — |
| `pedido_id` | inteiro | gerado auto | — |
| `produto_id` | inteiro | ✅ | Deve existir e estar ativo |
| `quantidade` | inteiro | ✅ | Maior que zero |
| `preco_unitario` | decimal | gerado auto | Copiado do produto no momento do pedido |

> **Importante:** O `preco_unitario` deve ser copiado do produto no momento da criação do pedido — não referencie o preço atual do produto, pois ele pode mudar.

> **`valor_total` do pedido:** Deve ser calculado automaticamente como a soma de `quantidade × preco_unitario` de todos os itens. Sempre que os itens mudarem, o `valor_total` deve ser recalculado.

---

## 🔄 Status do Pedido

O pedido segue um fluxo de status com transições válidas. Transições inválidas devem retornar `400` com mensagem clara.

```
PENDENTE ──→ CONFIRMADO ──→ EM_PREPARO ──→ ENVIADO ──→ ENTREGUE
    │               │
    └───────────────┴──→ CANCELADO
```

**Regras:**
- `PENDENTE` pode ir para `CONFIRMADO` ou `CANCELADO`
- `CONFIRMADO` pode ir para `EM_PREPARO` ou `CANCELADO`
- `EM_PREPARO` pode ir para `ENVIADO` apenas
- `ENVIADO` pode ir para `ENTREGUE` apenas
- `ENTREGUE` e `CANCELADO` são estados finais — nenhuma transição é permitida

---

## 🔌 Endpoints

### Clientes

| Método | Rota | Descrição | Sucesso | Erro |
|---|---|---|---|---|
| `POST` | `/clientes` | Cadastra um cliente | `201` | `400` / `409` |
| `GET` | `/clientes` | Lista todos | `200` | — |
| `GET` | `/clientes/{id}` | Busca por ID | `200` | `404` |
| `PUT` | `/clientes/{id}` | Atualiza | `200` | `400` / `404` |
| `DELETE` | `/clientes/{id}` | Remove | `204` | `404` / `400` |

> Não é permitido remover um cliente que possui pedidos. Retorne `400` com mensagem explicativa.

### Categorias

| Método | Rota | Descrição | Sucesso | Erro |
|---|---|---|---|---|
| `POST` | `/categorias` | Cadastra uma categoria | `201` | `400` / `409` |
| `GET` | `/categorias` | Lista todas | `200` | — |
| `DELETE` | `/categorias/{id}` | Remove | `204` | `404` / `400` |

> Não é permitido remover uma categoria que possui produtos vinculados.

### Produtos

| Método | Rota | Descrição | Sucesso | Erro |
|---|---|---|---|---|
| `POST` | `/produtos` | Cadastra um produto | `201` | `400` |
| `GET` | `/produtos` | Lista todos os ativos | `200` | — |
| `GET` | `/produtos/{id}` | Busca por ID | `200` | `404` |
| `PUT` | `/produtos/{id}` | Atualiza | `200` | `400` / `404` |
| `DELETE` | `/produtos/{id}` | Inativa o produto | `204` | `404` |

> O `DELETE` não remove o produto do banco — apenas marca como `ativo = false`. Produtos inativos não aparecem na listagem e não podem ser adicionados a novos pedidos.

### Pedidos

| Método | Rota | Descrição | Sucesso | Erro |
|---|---|---|---|---|
| `POST` | `/pedidos` | Cria um pedido com itens | `201` | `400` / `404` |
| `GET` | `/pedidos` | Lista todos | `200` | — |
| `GET` | `/pedidos/{id}` | Busca por ID com itens | `200` | `404` |
| `PATCH` | `/pedidos/{id}/status` | Avança o status do pedido | `200` | `400` / `404` |
| `DELETE` | `/pedidos/{id}` | Cancela o pedido | `204` | `400` / `404` |

> O `DELETE` não remove o pedido do banco — muda o status para `CANCELADO`, se a transição for permitida.

**Filtros disponíveis em `GET /pedidos`:**

```
?status=PENDENTE
?cliente_id=1
?status=CONFIRMADO&cliente_id=2
```

> Lista vazia não é erro — retorne `200` com `[]`.

### Relatórios

Os endpoints de relatório aceitam os query params `data_inicio` e `data_fim` (formato `YYYY-MM-DD`). **Ambos são obrigatórios.**

| Método | Rota | Descrição | Sucesso |
|---|---|---|---|
| `GET` | `/relatorios/pedidos/resumo` | Total de pedidos e receita por status no período | `200` |
| `GET` | `/relatorios/pedidos/por-dia` | Receita e quantidade de pedidos por dia | `200` |
| `GET` | `/relatorios/produtos/mais-vendidos` | Ranking de produtos por quantidade e receita | `200` |
| `GET` | `/relatorios/categorias/receita` | Receita total por categoria no período | `200` |
| `GET` | `/relatorios/clientes/ticket-medio` | Ticket médio por cliente no período | `200` |

---

## ✅ Exemplos de requisição e resposta

### Criar pedido

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "cliente_id": 1,
    "itens": [
      { "produto_id": 3, "quantidade": 2 },
      { "produto_id": 7, "quantidade": 1 }
    ]
  }'
```

Resposta `201`:
```json
{
  "id": 1,
  "cliente": { "id": 1, "nome": "Ana Lima" },
  "status": "PENDENTE",
  "valor_total": 299.70,
  "itens": [
    { "produto_id": 3, "nome": "Camiseta Azul", "quantidade": 2, "preco_unitario": 89.90, "subtotal": 179.80 },
    { "produto_id": 7, "nome": "Boné Preto", "quantidade": 1, "preco_unitario": 119.90, "subtotal": 119.90 }
  ],
  "criado_em": "2026-06-01T10:00:00",
  "atualizado_em": "2026-06-01T10:00:00"
}
```

---

### Avançar status

```bash
curl -X PATCH http://localhost:8080/pedidos/1/status \
  -H "Content-Type: application/json" \
  -d '{ "status": "CONFIRMADO" }'
```

Resposta `200`:
```json
{
  "id": 1,
  "status": "CONFIRMADO",
  "atualizado_em": "2026-06-01T10:15:00"
}
```

Resposta `400` — transição inválida:
```json
{
  "timestamp": "2026-06-01T10:15:00",
  "status": 400,
  "mensagem": "Transição inválida: pedido ENTREGUE não pode mudar de status."
}
```

---

### Relatório — Resumo por status

```bash
curl "http://localhost:8080/relatorios/pedidos/resumo?data_inicio=2026-01-01&data_fim=2026-01-31"
```

```json
{
  "periodo": { "inicio": "2026-01-01", "fim": "2026-01-31" },
  "resumo": [
    { "status": "ENTREGUE", "quantidade": 312, "receita": 48750.00 },
    { "status": "CANCELADO", "quantidade": 27, "receita": 0.00 },
    { "status": "PENDENTE", "quantidade": 14, "receita": 2100.00 }
  ],
  "total_pedidos": 353,
  "receita_total": 50850.00,
  "taxa_cancelamento": "7.65%"
}
```

---

### Relatório — Produtos mais vendidos

```bash
curl "http://localhost:8080/relatorios/produtos/mais-vendidos?data_inicio=2026-01-01&data_fim=2026-01-31"
```

```json
{
  "periodo": { "inicio": "2026-01-01", "fim": "2026-01-31" },
  "produtos": [
    { "posicao": 1, "id": 3, "nome": "Camiseta Azul", "categoria": "Roupas", "quantidade_vendida": 97, "receita": 8720.30 },
    { "posicao": 2, "id": 7, "nome": "Boné Preto", "categoria": "Acessórios", "quantidade_vendida": 84, "receita": 10071.60 }
  ]
}
```

---

### Relatório — Ticket médio por cliente

```bash
curl "http://localhost:8080/relatorios/clientes/ticket-medio?data_inicio=2026-01-01&data_fim=2026-01-31"
```

```json
{
  "periodo": { "inicio": "2026-01-01", "fim": "2026-01-31" },
  "clientes": [
    { "posicao": 1, "id": 2, "nome": "Carlos Souza", "total_pedidos": 8, "receita_total": 4200.00, "ticket_medio": 525.00 },
    { "posicao": 2, "id": 1, "nome": "Ana Lima", "total_pedidos": 12, "receita_total": 5100.00, "ticket_medio": 425.00 }
  ]
}
```

---

## ❌ Exemplos de erro

### Produto inativo no pedido — `400`

```json
{
  "timestamp": "2026-06-01T10:00:00",
  "status": 400,
  "mensagem": "O produto 'Camiseta Azul' está inativo e não pode ser adicionado ao pedido."
}
```

### Estoque insuficiente — `400`

```json
{
  "timestamp": "2026-06-01T10:00:00",
  "status": 400,
  "mensagem": "Estoque insuficiente para o produto 'Boné Preto'. Disponível: 3, solicitado: 5."
}
```

### Erro de validação em campos — `400`

```json
{
  "timestamp": "2026-06-01T10:00:00",
  "status": 400,
  "mensagem": "Erro de validação em campos",
  "erros": [
    { "campo": "itens", "mensagem": "O pedido deve ter pelo menos 1 item." }
  ]
}
```

---

## 🏗️ Organização do código

```
controller  →  recebe a requisição e devolve a resposta HTTP
service     →  aplica as regras de negócio e as transições de status
repository  →  executa as queries no banco
model       →  entidades JPA com os relacionamentos
dto         →  objetos de entrada (request) e saída (response)
projection  →  interfaces de projeção para queries nativas com agregação
mapper      →  conversão entre entidade e DTO
exception   →  exceções customizadas e handler global
```

> Use as funções do banco (`SUM`, `COUNT`, `GROUP BY`, `JOIN`) nos relatórios — não traga todos os registros para a memória e processe em Java.

---

## ⭐ Bônus

Terminou tudo? Tente implementar também:

- **Filtro de pedidos por status** — `GET /pedidos?status=PENDENTE`
- **Filtro de produtos por categoria** — `GET /produtos?categoria_id=1`
- **Paginação nas listagens** — `GET /pedidos?page=0&size=20`
- **Restaurar estoque ao cancelar** — ao cancelar um pedido, devolva a quantidade dos itens ao estoque de cada produto

---

## 🌱 Script de seed

Um arquivo `import.sql` está disponível na raiz do repositório. Ele popula o banco com:

- 5 categorias
- 15 produtos distribuídos entre as categorias
- 10 clientes
- Pedidos em diferentes status distribuídos em 3 meses

Para executar:

```bash
psql -U seu_usuario -d seu_banco -f import.sql
```

> Os dados são distribuídos de forma irregular entre clientes, produtos e dias — para que os filtros de data e os relatórios façam diferença nos resultados.

---

## 🚀 Como entregar

Consulte o [CONTRIBUTING.md](../../CONTRIBUTING.md) para ver como compartilhar sua solução.

---

## 💡 Dicas

- **Comece pelo modelo de dados** — desenhe as relações no papel antes de abrir o IDE
- **Implemente o CRUD antes dos relatórios** — você vai precisar das entidades criadas e populadas para testar
- **O `valor_total` do pedido é calculado** — some `quantidade × preco_unitario` de cada item no momento da criação
- **O `preco_unitario` do item é fixo** — copie o preço do produto ao criar o item, não guarde referência
- **Valide o estoque antes de criar o pedido** — verifique se tem unidades disponíveis e atualize após confirmar
- **Testes automatizados e Docker são diferenciais**, não requisitos

Boa sorte! 💙