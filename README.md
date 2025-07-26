# Locker API: Serviço de Guarda-Volumes

Bem-vindo ao **Locker API**, uma robusta API RESTful desenvolvida com **Spring Boot** para o gerenciamento eficiente de um serviço de guarda-volumes. Esta aplicação oferece funcionalidades completas para administrar armários, localizações, clientes, aluguéis e relatórios, além de um sistema de autenticação e autorização baseado em JWT.

---

## 📌 Visão Geral do Projeto

O Locker API foi projetado para ser o **backend** de uma solução de guarda-volumes estilo container, permitindo a criação, consulta, atualização e exclusão de recursos essenciais. Inclui:

- Serviço de e-mail para notificações
- Relatórios para insights sobre ocupação e aluguéis ativos

---

## 🚀 Funcionalidades Principais

### 🔒 Gerenciamento de Armários
- Criar, visualizar, atualizar (total ou status) e deletar armários
- Listar armários paginados ou filtrados por localização

### 📍 Gerenciamento de Localizações
- Nome, endereço, cidade e descrição
- CRUD completo para pontos físicos

### 👤 Gerenciamento de Clientes
- Cadastro, atualização e exclusão de clientes
- Buscar por ID, e-mail ou listar todos

### 📦 Aluguéis
- Criar e finalizar aluguéis vinculados a clientes e armários
- Buscar por ID ou listar todos os aluguéis de um cliente

### 📊 Relatórios
- **Aluguéis Ativos**: lista de aluguéis em andamento
- **Ocupação por Localização**: contagem por status (disponível, ocupado, manutenção, fora de serviço)

### 📧 Serviço de E-mail
- Boas-vindas
- Confirmações de alteração de dados e exclusão de conta
- Confirmação de aluguel

### 🔐 Segurança Robusta
- JWT para autenticação e autorização
- BCrypt para criptografia de senhas
- Perfis de acesso (`ROLE_ADMIN`, `ROLE_USUARIO`)
- Tratamento de exceções personalizadas (`404`, `409`, `400`, `500`)

### 📄 Documentação Interativa
- **Springdoc OpenAPI (Swagger UI)**: disponível em `/swagger-ui.html`

---

## 🛠 Tecnologias Utilizadas

- **Spring Boot** (v2.7.6)
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok** (v1.18.30)
- **Springdoc OpenAPI UI** (v1.7.0)
- **Spring Mail**
- **Freemarker**
- **JJWT** (v0.9.1)
- **Docker**
- **Maven**

---

## ⚙️ Configuração e Execução

### ✅ Pré-requisitos

- JDK 17+
- Maven 3.x
- Docker (opcional)
- Servidor PostgreSQL

### 🧩 Configuração do Banco de Dados

Definida em: `src/main/resources/application.properties`

Usa variáveis de ambiente:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Scripts:
- `schema.sql`: criação do schema
- `data.sql`: dados iniciais

### 🔑 Variáveis de Ambiente

| Variável                | Descrição                                     |
|-------------------------|-----------------------------------------------|
| `SPRING_DATASOURCE_URL` | URL JDBC do PostgreSQL                        |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco                       |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco                        |
| `jwt.secret`            | Chave secreta JWT (ex: `Minh@Ch@v3VemSer@9edit`) |
| `jwt.expiration`        | Expiração do token em ms (ex: `86400000`)     |

---
