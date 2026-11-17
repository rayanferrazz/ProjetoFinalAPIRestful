# Projeto Final — API RESTful para Rede Social

![Java](https://img.shields.io/badge/Java-11-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.16-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-database-4169E1?logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-Springfox-85EA2D?logo=swagger&logoColor=black)

API RESTful de uma **rede social**, desenvolvida como trabalho final da disciplina de **API RESTful** da Residência de Software do **Serratec** (Grupo 1).

A aplicação permite cadastrar usuários, publicar postagens, comentar e seguir/deixar de seguir outros usuários. Os endpoints são protegidos por **HTTP Basic Auth** e documentados com **Swagger (Springfox)**.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Modelo de domínio](#modelo-de-domínio)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do banco](#configuração-do-banco)
- [Como executar](#como-executar)
- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
- [Exemplos de requisição](#exemplos-de-requisição)
- [Documentação Swagger](#documentação-swagger)
- [Testes](#testes)
- [Limitações e próximos passos](#limitações-e-próximos-passos)

---

## Funcionalidades

- **Usuários**
  - Cadastro com nome, sobrenome, e-mail, senha, confirmação de senha e data de nascimento;
  - Listagem paginada e busca por nome;
  - Consulta, atualização e exclusão por ID;
  - Validação de e-mail duplicado e confirmação de senha.
- **Postagens**
  - Criação, listagem, consulta, atualização e exclusão;
  - Associação com o usuário autor.
- **Comentários**
  - Criação, listagem, consulta, atualização e exclusão;
  - Associação com uma postagem.
- **Relações (seguir)**
  - Seguir um usuário;
  - Listar seguidores e pessoas seguidas;
  - Deixar de seguir.
- **Segurança**
  - HTTP Basic Auth com usuário em memória;
  - Sessões stateless e CSRF desabilitado (API).
- **Documentação**
  - Swagger UI via Springfox.

## Modelo de domínio

```text
Usuario 1 ─── N Postagem 1 ─── N Comentario
   │
   └── N Relacao (seguidor ↔ seguido) ── chave composta (RelacaoFK)
```

| Entidade | Principais campos |
| --- | --- |
| `Usuario` | `id`, `nome`, `sobrenome`, `email`, `senha`, `dataNascimento` |
| `Postagem` | `id`, `conteudo`, `dataCriacao`, `usuario` |
| `Comentario` | `id`, `texto`, `dataCriacao`, `postagem` |
| `Relacao` | `id` (`RelacaoFK`: seguidor + seguido), `dataInicioSeguimento` |

## Tecnologias

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Java 11 |
| Framework | Spring Boot 2.7.16 (Web, Data JPA, Validation, Security) |
| Banco de dados | PostgreSQL (H2 disponível como alternativa) |
| Documentação | Springfox 3.0.0 (Swagger 2) |
| Build | Maven Wrapper (`mvnw`) |
| Utilitários | Spring Boot DevTools |

## Estrutura do projeto

```text
src/main/java/org/serratec/api/trabalhofinalgrupo1
├── TrabalhoFinalGrupo1Application.java
├── config/
│   ├── SecurityConfig.java         # Basic Auth + stateless
│   └── SwaggerConfig.java          # Docket do Springfox
├── controller/
│   ├── ComentarioController.java
│   ├── PostagemController.java
│   ├── RelacaoController.java
│   └── UsuarioController.java
├── DTO/                            # DTOs de entrada e saída
├── exception/                      # Exceções e handler global
├── model/                          # Entidades JPA
├── repository/                     # Spring Data JPA
└── service/                        # Regras de negócio
```

## Pré-requisitos

- **Java 11** ou superior;
- **PostgreSQL** em execução (porta padrão `5432`);
- Maven não é obrigatório: use o wrapper incluído.

## Configuração do banco

Crie o banco:

```sql
CREATE DATABASE "TrabalhoFinalAPI";
```

Configuração padrão em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/TrabalhoFinalAPI
spring.datasource.username=postgres
spring.datasource.password=serratec

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> ⚠️ As credenciais acima são apenas para desenvolvimento local. Em produção, use variáveis de ambiente:
>
> ```properties
> spring.datasource.url=${DB_URL}
> spring.datasource.username=${DB_USER}
> spring.datasource.password=${DB_PASSWORD}
> ```

O schema é criado/atualizado automaticamente pelo Hibernate (`ddl-auto=update`). Não é necessário rodar scripts SQL manualmente.

### Alternativa com H2

Há configuração comentada no `application.properties` para usar H2 em arquivo. Descomente as linhas de H2 e comente as de PostgreSQL.

## Como executar

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Autenticação

Todos os endpoints exigem **HTTP Basic Auth**. Usuário padrão definido em `SecurityConfig`:

| Campo | Valor |
| --- | --- |
| Usuário | `administrador` |
| Senha | `123456` |
| Perfil | `ADMIN` |

Exemplo com `curl`:

```bash
curl -u administrador:123456 http://localhost:8080/usuarios
```

No Postman/Insomnia, use a aba **Authorization → Basic Auth**.

> Essas credenciais são fixas e servem apenas para estudo. Para produção, implemente usuários no banco, hash de senha e JWT/OAuth2.

## Endpoints

Base: `http://localhost:8080` (todas as rotas exigem autenticação)

### Usuários

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/usuarios` | Lista todos os usuários (DTO sem senha) |
| `GET` | `/usuarios/paginado` | Lista paginada (5 por página, ordenada por nome) |
| `GET` | `/usuarios/nome?paramNome=...` | Busca paginada por nome |
| `GET` | `/usuarios/{id}` | Busca usuário por ID |
| `POST` | `/usuarios` | Cadastra usuário |
| `PUT` | `/usuarios/{id}` | Atualiza usuário |
| `DELETE` | `/usuarios/{id}` | Remove usuário |

### Postagens

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/postagens` | Lista postagens |
| `GET` | `/postagens/{id}` | Busca postagem por ID |
| `POST` | `/postagens` | Cria postagem |
| `PUT` | `/postagens/{id}` | Atualiza postagem |
| `DELETE` | `/postagens/{id}` | Remove postagem |

### Comentários

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/comentarios` | Lista comentários |
| `GET` | `/comentarios/{id}` | Busca comentário por ID |
| `POST` | `/comentarios` | Cria comentário |
| `PUT` | `/comentarios/{id}` | Atualiza comentário |
| `DELETE` | `/comentarios/{id}` | Remove comentário |

### Relações

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/relacoes` | Lista todas as relações |
| `GET` | `/relacoes/{id}` | Busca relação por ID |
| `GET` | `/relacoes/buscarSeguidores/{id}` | Lista seguidores do usuário |
| `GET` | `/relacoes/buscarSeguindo/{id}` | Lista quem o usuário segue |
| `POST` | `/relacoes` | Segue um usuário |
| `DELETE` | `/relacoes/{seguidoID}/{seguidorID}` | Deixa de seguir |

## Exemplos de requisição

### Criar usuário

```http
POST /usuarios
Authorization: Basic YWRtaW5pc3RyYWRvcjoxMjM0NTY=
Content-Type: application/json
```

```json
{
  "nome": "Rayan",
  "sobrenome": "Ferraz",
  "email": "rayan@email.com",
  "senha": "senha1234",
  "confirmaSenha": "senha1234",
  "dataNascimento": "2000-05-10"
}
```

### Criar postagem

```json
{
  "conteudo": "Minha primeira postagem!",
  "dataCriacao": "2024-01-15T10:30:00",
  "usuario": { "id": 1 }
}
```

### Criar comentário

```json
{
  "texto": "Que legal!",
  "dataCriacao": "2024-01-15T11:00:00",
  "postagem": { "id": 1 }
}
```

### Seguir usuário

```json
{
  "seguidoId": 2,
  "seguidorId": 1
}
```

### Paginação e busca

```http
GET /usuarios/paginado?page=0&size=5&sort=nome,asc
GET /usuarios/nome?paramNome=Rayan&page=0&size=5
```

## Documentação Swagger

Com a aplicação rodando, acesse:

```text
http://localhost:8080/swagger-ui/
```

Como a segurança está aplicada a **todas** as rotas, o navegador solicitará o usuário e a senha do Basic Auth (`administrador` / `123456`).

> O Springfox 3.0.0 é antigo e pode apresentar incompatibilidades com versões recentes do Spring Boot. Por isso o projeto fixa `spring.mvc.pathmatch.matching-strategy=ant-path-matcher`. Para novos projetos, prefira **springdoc-openapi**.

## Testes

```bash
# Windows
mvnw.cmd test

# Linux/macOS
./mvnw test
```

O projeto possui o teste de contexto gerado pelo Spring Initializr.

## Limitações e próximos passos

- **Credenciais fixas em memória** — não há cadastro/login real de usuários da API.
- **Senhas em texto puro** — não há hash (BCrypt) no cadastro.
- **Sem CORS configurado** — um front-end em outra origem precisará de configuração de CORS.
- **DTOs de entrada recebem entidades** — `PostagemInserirDTO` e `ComentarioInserirDTO` expõem `Usuario`/`Postagem` diretamente; o ideal é receber apenas IDs.
- **Sem paginação em postagens/comentários**.
- **Swagger protegido** — não há liberação específica para `/swagger-ui/**` e `/v3/api-docs/**`.
- **Springfox descontinuado** — migrar para springdoc-openapi.
- **Sem testes de integração** cobrindo as regras de negócio.
- **Sem tratamento de datas** com `LocalDate`/`LocalDateTime` — o projeto usa `java.util.Date`.

## Contexto acadêmico

Trabalho final da disciplina de **API RESTful** — Residência de Software **Serratec** — **Grupo 1**.

## Autor

Desenvolvido por **Rayan Ferraz** e pelo Grupo 1.

- GitHub: [@rayanferrazz](https://github.com/rayanferrazz)
