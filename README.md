# API REST - CRUD de Usuário com Spring Boot e Spring Security

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white" />
  <img src="https://img.shields.io/badge/H2-007396?style=for-the-badge&logo=h2&logoColor=white" />
</p>

## Descrição

Este projeto é uma **API RESTful** que implementa as operações básicas de CRUD (**Create**, **Read**, **Update**, **Delete**) para a entidade **Usuário**. A API foi desenvolvida utilizando **Spring Boot** para facilitar o desenvolvimento, **JWT** (JSON Web Tokens) com **Spring Security** para garantir a segurança da API, e utiliza um banco de dados em memória **H2** para persistência de dados.

### Funcionamento

A API permite que os usuários realizem operações de gerenciamento, como cadastrar, consultar, atualizar e excluir usuários. As operações são protegidas por autenticação JWT, que garante que apenas usuários autenticados possam acessar as rotas de CRUD.

1. **Operações CRUD na Entidade Usuário:**
   - **Criar Usuário**: Cadastro de novos usuários (POST).
   - **Consultar Usuário**: Consultar detalhes de um usuário pelo ID (GET).
   - **Atualizar Usuário**: Atualização dos dados de um usuário existente (PUT).
   - **Deletar Usuário**: Exclusão de um usuário pelo ID (DELETE).

2. **Autenticação e Autorização com JWT**:
   - Endpoint de **login**: `/login`, onde o usuário envia seu e-mail e senha para receber um token JWT.
   - O token JWT é obrigatório para acessar as rotas de CRUD.
   - As senhas são armazenadas de forma criptografada usando **BCrypt**.

3. **Banco de Dados H2**:
   - O banco de dados é configurado para funcionar em memória, com a criação automática da tabela de usuários ao iniciar a aplicação.

4. **Validações**:
   - O campo **e-mail** é único e validado quanto ao formato.
   - O campo **senha** deve ter no mínimo 6 caracteres.

## Tecnologias Utilizadas

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
- ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)
- ![H2 Database](https://img.shields.io/badge/H2-007396?style=for-the-badge&logo=h2&logoColor=white)
- ![BCrypt](https://img.shields.io/badge/BCrypt-4B8BBE?style=for-the-badge)

## Pré-requisitos

Certifique-se de ter instalado as seguintes ferramentas:

- **Java 11** ou superior
- **Maven** (para gestão de dependências)

## Como Executar o Projeto

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/seu-usuario/sua-api.git
## Uso

1. Inicie a aplicação usando o Maven.
2. A API estará acessível em http://localhost:8080

## Endpoints da API

### Bearer Token
É necessário incluir o token Bearer no cabeçalho de autorização para realizar as operações da API. 
1. Buscar usuário
2. Atualizar usuário
3. Deletar usuário

**POST - Criar usuário**
```markdown
POST /register 
```
```
{
  "name": "Thiago",
  "email": "trabalho@gmail.com",
  "password": "Trabalho123@"
}
```
**POST - Realizar login**
```markdown
POST /login 
```
```
{
  "email": "trabalho@gmail.com",
  "password": "Trabalho123@"
}
```
**GET - Buscar usuário pelo id**
```markdown
GET /accounts/manage/{id}
```
**PUT - Atualizar dados do usuário**
```markdown
PUT /accounts/manage/{id}
```
```
{
  "name": "Pedro",
  "password": "Faculdade@"
}
```
**DELETE - Deletar usuário pelo id**
```markdown
DELETE /accounts/manage/{id}
```
## Swagger

Para executar o projeto, assegure-se de que todos os pré-requisitos estão atendidos. Se desejar acessar a documentação da API através do Swagger, você pode fazê-lo utilizando o seguinte link:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)


