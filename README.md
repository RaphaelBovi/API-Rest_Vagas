# API Rest Vagas

API REST desenvolvida em Spring Boot para gerenciamento de currículos e busca de vagas de emprego através da integração com a API Adzuna.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades](#funcionalidades)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração e Instalação](#configuração-e-instalação)
- [Endpoints da API](#endpoints-da-api)
- [Exemplos de Uso](#exemplos-de-uso)
- [Validações e Regras de Negócio](#validações-e-regras-de-negócio)
- [Tratamento de Erros](#tratamento-de-erros)
- [Integração com API Adzuna](#integração-com-api-adzuna)

## 🎯 Sobre o Projeto

A **API Rest Vagas** é uma aplicação backend que permite o cadastro e gerenciamento de currículos, além de realizar buscas inteligentes de vagas de emprego baseadas nas habilidades, localização e perfil do candidato. A API integra-se com a plataforma Adzuna para fornecer resultados de vagas relevantes.

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 4.0.1** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring WebFlux** - Cliente HTTP reativo para integração com APIs externas
- **PostgreSQL** - Banco de dados relacional
- **Lombok** - Redução de boilerplate
- **Spring HATEOAS** - Suporte a hipermedia
- **Bean Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

## ✨ Funcionalidades

### 1. Gerenciamento de Currículos

#### 1.1. Criar Currículo (`POST /api/curriculos`)
- Cadastro completo de currículo com informações pessoais
- Suporte para múltiplos cursos complementares (máximo 15)
- Cadastro de idiomas com níveis de proficiência
- Cadastro de skills técnicas com níveis de conhecimento
- Validação completa de dados de entrada

#### 1.2. Buscar Currículo por ID (`GET /api/curriculos/{id}`)
- Recupera um currículo específico pelo seu identificador
- Retorna todas as informações associadas (cursos, idiomas, skills)

#### 1.3. Atualizar Currículo (`PUT /api/curriculos/{id}`)
- Atualização completa de todas as informações do currículo
- Permite modificar cursos, idiomas e skills
- Mantém as mesmas validações do cadastro

#### 1.4. Deletar Currículo (`DELETE /api/curriculos/{id}`)
- Remove um currículo do sistema
- Exclusão em cascata de todos os dados relacionados

### 2. Busca de Vagas de Emprego

#### 2.1. Buscar Vagas por Currículo (`GET /api/curriculos/{id}/vagas`)
- Busca inteligente de vagas baseada no perfil do candidato
- Utiliza as skills do currículo para construir a query de busca
- Considera a localização (residência) do candidato
- Integração com a API Adzuna para obter vagas reais
- Retorna lista de vagas relevantes com informações completas

### 3. Validações e Regras de Negócio

- **Validação de dados obrigatórios**: Nome, residência, data de nascimento, nível de escolaridade
- **Validação de data**: Data de nascimento deve ser no passado
- **Limite de cursos**: Máximo de 15 cursos complementares por currículo
- **Skills obrigatórias**: Pelo menos uma skill deve ser informada
- **Validação de níveis**: Validação de níveis de idioma e skills

### 4. Tratamento de Erros

- Tratamento global de exceções com respostas padronizadas
- Mensagens de erro descritivas e informativas
- Códigos HTTP apropriados para cada tipo de erro
- Logging detalhado para debugging

## 📁 Estrutura do Projeto

```
ProjectEmpregar/
├── src/
│   ├── main/
│   │   ├── java/VagasSkills/
│   │   │   ├── config/          # Configurações (WebClient)
│   │   │   ├── controller/      # Controladores REST
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # Entidades JPA
│   │   │   │   └── enums/       # Enumeradores
│   │   │   ├── exception/       # Exceções customizadas
│   │   │   ├── repository/      # Repositórios JPA
│   │   │   ├── service/         # Lógica de negócio
│   │   │   └── Project/         # Classe principal
│   │   └── resources/
│   │       ├── application.yml          # Configurações principais
│   │       ├── application-dev.yml       # Configurações de desenvolvimento
│   │       └── application-prod.yml      # Configurações de produção
│   └── test/                    # Testes
├── pom.xml                      # Dependências Maven
├── test-api.http               # Arquivo de testes HTTP
└── README.md                   # Documentação
```

## 📦 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+
- Conta na API Adzuna (para obter App ID e App Key)

## ⚙️ Configuração e Instalação

### 1. Clone o repositório

```bash
git clone https://github.com/RaphaelBovi/API-Rest_Vagas.git
cd API-Rest_Vagas
```

### 2. Configure o banco de dados PostgreSQL

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE empregar_db;
```

### 3. Configure as variáveis de ambiente

Crie um arquivo `.env` ou configure as seguintes variáveis:

```env
# Database
DATABASE_URL=jdbc:postgresql://localhost:5432/empregar_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

# Adzuna API
ADZUNA_APP_ID=seu_app_id
ADZUNA_APP_KEY=sua_app_key
ADZUNA_TIMEOUT=5000

# Spring
SPRING_PROFILES_ACTIVE=dev
PORT=8080
```

### 4. Execute a aplicação

```bash
# Usando Maven Wrapper
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## 🔌 Endpoints da API

### Base URL
```
http://localhost:8080/api/curriculos
```

### Endpoints Disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/curriculos` | Criar um novo currículo |
| GET | `/api/curriculos/{id}` | Buscar currículo por ID |
| PUT | `/api/curriculos/{id}` | Atualizar currículo |
| DELETE | `/api/curriculos/{id}` | Deletar currículo |
| GET | `/api/curriculos/{id}/vagas` | Buscar vagas para um currículo |

## 📝 Exemplos de Uso

### Criar um Currículo

```http
POST http://localhost:8080/api/curriculos
Content-Type: application/json

{
  "nome": "João Silva",
  "residencia": "São Paulo",
  "dataNascimento": "1990-05-15",
  "nivelEscolaridade": "Superior",
  "cursosComplementares": [
    {
      "nome": "Java Avançado",
      "instituicao": "Alura",
      "cargaHoraria": 40
    },
    {
      "nome": "Spring Boot",
      "instituicao": "Udemy",
      "cargaHoraria": 30
    }
  ],
  "idiomas": [
    {
      "nome": "Inglês",
      "nivel": "Avançado"
    }
  ],
  "skills": [
    {
      "nome": "Java",
      "nivel": "Avançado"
    },
    {
      "nome": "Spring Boot",
      "nivel": "Intermediário"
    }
  ]
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "residencia": "São Paulo",
  "dataNascimento": "1990-05-15",
  "nivelEscolaridade": "Superior",
  "cursosComplementares": [
    {
      "id": 1,
      "nome": "Java Avançado",
      "instituicao": "Alura",
      "cargaHoraria": 40
    }
  ],
  "idiomas": [
    {
      "id": 1,
      "nome": "Inglês",
      "nivel": "Avançado"
    }
  ],
  "skills": [
    {
      "id": 1,
      "nome": "Java",
      "nivel": "Avançado"
    }
  ]
}
```

### Buscar Vagas para um Currículo

```http
GET http://localhost:8080/api/curriculos/1/vagas
```

**Resposta (200 OK):**
```json
[
  {
    "title": "Desenvolvedor Java Senior",
    "company": "Tech Solutions",
    "location": "São Paulo",
    "description": "Vaga para desenvolvedor Java com experiência em Spring Boot...",
    "url": "https://example.com/vaga/123"
  }
]
```

### Atualizar um Currículo

```http
PUT http://localhost:8080/api/curriculos/1
Content-Type: application/json

{
  "nome": "João Silva Atualizado",
  "residencia": "São Paulo",
  "dataNascimento": "1990-05-15",
  "nivelEscolaridade": "Superior Completo",
  "skills": [
    {
      "nome": "Java",
      "nivel": "Especialista"
    },
    {
      "nome": "Spring Boot",
      "nivel": "Avançado"
    }
  ]
}
```

### Deletar um Currículo

```http
DELETE http://localhost:8080/api/curriculos/1
```

**Resposta (204 No Content)**

## ✅ Validações e Regras de Negócio

### Validações de Entrada

- **Nome**: Obrigatório, não pode estar em branco
- **Residência**: Obrigatória, não pode estar em branco
- **Data de Nascimento**: Obrigatória, deve ser uma data no passado
- **Nível de Escolaridade**: Obrigatório, não pode estar em branco
- **Skills**: Obrigatório ter pelo menos uma skill
- **Cursos Complementares**: Máximo de 15 cursos por currículo

### Regras de Negócio

1. **Limite de Cursos**: Um currículo pode ter no máximo 15 cursos complementares
2. **Skills Obrigatórias**: Todo currículo deve ter pelo menos uma skill cadastrada
3. **Busca de Vagas**: A busca utiliza as skills e a localização do candidato para encontrar vagas relevantes

## 🚨 Tratamento de Erros

A API retorna respostas padronizadas para diferentes tipos de erros:

### Currículo Não Encontrado (404)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Currículo com ID 999 não encontrado",
  "path": "/api/curriculos"
}
```

### Validação Falhou (400)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Erros de validação nos dados fornecidos",
  "errors": {
    "nome": "Nome é obrigatório",
    "dataNascimento": "Data de nascimento deve ser no passado"
  }
}
```

### Máximo de Cursos Excedido (400)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Número máximo de cursos complementares excedido. Fornecido: 16, Máximo permitido: 15"
}
```

### Erro na API Adzuna (503)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 503,
  "error": "Service Unavailable",
  "message": "Erro ao buscar vagas na API Adzuna. Tente novamente mais tarde."
}
```

## 🔗 Integração com API Adzuna

A API integra-se com a [API Adzuna](https://developer.adzuna.com/) para buscar vagas de emprego reais. A integração utiliza:

- **WebClient** do Spring WebFlux para chamadas HTTP reativas
- **Timeout configurável** para evitar travamentos
- **Tratamento de erros** robusto para falhas de conexão
- **Query inteligente** construída a partir das skills do currículo

### Como a Busca Funciona

1. O sistema coleta as skills do currículo
2. Constrói uma query de busca combinando as skills
3. Utiliza a localização (residência) do candidato
4. Faz a requisição para a API Adzuna
5. Retorna as vagas encontradas formatadas

## 🧪 Testes

O projeto inclui um arquivo `test-api.http` com exemplos de requisições para testar todos os endpoints da API. Você pode usar este arquivo com extensões como REST Client do VS Code ou IntelliJ HTTP Client.

## 📄 Licença

Este projeto está sob a licença MIT.

## 👤 Autor

**Raphael Bovi**

- GitHub: [@RaphaelBovi](https://github.com/RaphaelBovi)
- Repositório: [API-Rest_Vagas](https://github.com/RaphaelBovi/API-Rest_Vagas)

## 🤝 Contribuindo

Contribuições são sempre bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

---

Desenvolvido com ❤️ usando Spring Boot

