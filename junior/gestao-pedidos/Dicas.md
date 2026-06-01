# 📖 Recursos de Estudo — Gestão de Pedidos

Sentiu dificuldade em algum ponto? Este arquivo reúne recursos para você estudar antes ou durante o desafio.

[← Voltar para o desafio](./README.md)

---

## 🔗 Relacionamentos JPA

Este desafio tem vários relacionamentos entre entidades. Entender `@OneToMany`, `@ManyToOne` e `@ManyToMany` é fundamental.

- [Baeldung — Hibernate One-To-Many (en)](https://www.baeldung.com/hibernate-one-to-many)
- [Baeldung — Hibernate Many-To-Many (en)](https://www.baeldung.com/hibernate-many-to-many)
- [Vídeo — Relacionamentos JPA na prática](https://www.youtube.com/results?search_query=jpa+relacionamentos+onetomany+manytomany+java+português)
- [Documentação Spring Data JPA — Associations (en)](https://docs.spring.io/spring-data/jpa/reference/jpa/entity-persistence.html)

---

## 🔄 Máquina de Estados (Status do Pedido)

O fluxo de status do pedido segue o padrão de máquina de estados. Entender como implementar transições válidas e rejeitar inválidas é uma das partes mais interessantes do desafio.

- [Vídeo — State Machine em Java](https://www.youtube.com/results?search_query=state+machine+java+spring+português)
- [Baeldung — State Design Pattern in Java (en)](https://www.baeldung.com/java-state-design-pattern)

---

## 🗄️ Queries com Agregação e JOIN

Os relatórios deste desafio são mais complexos que os anteriores — envolvem JOINs entre múltiplas tabelas e várias funções de agregação na mesma query.

- [PostgreSQL — Funções de agregação (en)](https://www.postgresql.org/docs/current/functions-aggregate.html)
- [SQLZoo — Tutoriais de JOIN interativos (en)](https://sqlzoo.net/wiki/The_JOIN_operation)
- [Vídeo — SQL com GROUP BY e JOIN na prática](https://www.youtube.com/results?search_query=sql+group+by+join+agregacao+portugues)

---

## 🎯 Interfaces de Projeção (Spring Data JPA)

Para mapear os resultados das queries nativas com agregação, você vai precisar de interfaces de projeção — o mesmo conceito do desafio anterior, agora com queries mais complexas.

- [Documentação oficial — Spring Data JPA Projections (en)](https://docs.spring.io/spring-data/jpa/reference/repositories/projections.html)
- [Baeldung — Spring Data JPA Projections (en)](https://www.baeldung.com/spring-data-jpa-projections)
- [Thorben Janssen — DTOs from Native Queries (en)](https://thorben-janssen.com/spring-data-jpa-dto-native-queries/)

---

## 🧱 Organização em camadas

Se está com dúvida sobre como separar as responsabilidades entre controller, service e repository:

- [Vídeo — Arquitetura em camadas no Spring Boot](https://www.youtube.com/results?search_query=spring+boot+arquitetura+camadas+controller+service+repository+português)
- [Baeldung — Spring Boot Application Layers (en)](https://www.baeldung.com/spring-boot-app-architecture)

---

## 🧪 Como testar a API

- [Documentação do Insomnia (en)](https://docs.insomnia.rest/)
- [Guia do curl (en)](https://curl.se/docs/manual.html)

---

## 🐳 Docker (opcional)

Se quiser subir a aplicação e o banco com Docker:

- [Docker — Guia de introdução (pt-BR)](https://docs.docker.com/get-started/)
- [Vídeo — Docker Compose com Spring Boot e PostgreSQL](https://www.youtube.com/results?search_query=docker+compose+spring+boot+postgresql+português)

---

Boa sorte! 💙