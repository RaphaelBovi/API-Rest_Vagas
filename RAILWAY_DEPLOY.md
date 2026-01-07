# Guia de Deploy no Railway

Este guia explica como fazer deploy do projeto no Railway com PostgreSQL.

## Pré-requisitos

1. Conta no Railway (https://railway.app)
2. Conta no GitHub/GitLab (para conectar repositório)
3. Credenciais da API Adzuna

## Passo a Passo

### 1. Criar Novo Projeto no Railway

1. Acesse https://railway.app
2. Faça login com sua conta GitHub/GitLab
3. Clique em "New Project"
4. Selecione "Deploy from GitHub repo"
5. Escolha este repositório

### 2. Criar Banco de Dados PostgreSQL

1. No dashboard do projeto Railway, clique em "+ New"
2. Selecione "Database" → "Add PostgreSQL"
3. O Railway criará automaticamente um banco PostgreSQL
4. **OU** use um banco PostgreSQL existente:
   - Host: `postgres-production-e12e.up.railway.app`
   - Username: `postgres`
   - Password: `RyzUTLHdHjIxlxlThzJbMsIxrPviUJlA`
   - Configure a variável `DATABASE_URL` no formato: `jdbc:postgresql://postgres-production-e12e.up.railway.app:5432/railway`
5. As credenciais aparecerão nas variáveis de ambiente automaticamente

### 3. Configurar Variáveis de Ambiente

No dashboard do Railway, vá em Settings → Variables e adicione:

```bash
SPRING_PROFILES_ACTIVE=prod
```

**Se estiver usando um banco PostgreSQL existente, configure também:**

```bash
DATABASE_URL=jdbc:postgresql://postgres-production-e12e.up.railway.app:5432/railway
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=RyzUTLHdHjIxlxlThzJbMsIxrPviUJlA
```

**Nota:** 
- As credenciais da API Adzuna já estão configuradas como valores padrão no `application.yml`
- O JPA está configurado com `ddl-auto: update`, então as tabelas serão criadas automaticamente no primeiro deploy
- Se você adicionar um novo serviço PostgreSQL no Railway, as variáveis do banco (`DATABASE_URL`, `PGUSER`, `PGPASSWORD`) serão criadas automaticamente

### 4. Configurar Build e Deploy

O Railway detecta automaticamente projetos Spring Boot. O arquivo `Procfile` já está configurado:

```
web: java -jar target/Project-0.0.1-SNAPSHOT.jar
```

### 5. Conectar Banco de Dados ao Serviço

1. No dashboard, clique no serviço da aplicação
2. Vá em "Settings" → "Connect Database"
3. Selecione o serviço PostgreSQL criado
4. O Railway conectará automaticamente

### 6. Deploy

1. Faça commit e push das alterações para o repositório
2. O Railway detectará automaticamente e fará o deploy
3. Acompanhe os logs em "Deployments"

## Verificação

Após o deploy:

1. Acesse a URL fornecida pelo Railway
2. Teste o endpoint: `https://seu-app.railway.app/api/curriculos`
3. Verifique os logs para garantir que a conexão com o banco está funcionando

## Troubleshooting

### Erro de Conexão com Banco

- Verifique se as variáveis de ambiente do banco estão configuradas
- Confirme que o serviço PostgreSQL está rodando
- Verifique os logs do Railway

### Erro de Build

- Verifique se o Java 17 está configurado
- Confirme que o Maven está instalado
- Veja os logs de build no Railway

### Erro na API Adzuna

- As credenciais já estão configuradas no `application.yml` como valores padrão
- Se necessário, verifique se as variáveis de ambiente `ADZUNA_APP_ID` e `ADZUNA_APP_KEY` não estão sobrescrevendo os valores padrão incorretamente
- Confirme que as credenciais estão corretas: App ID: `7723771c`, App Key: `a4e84f857e72f1e171d605c4ef2c275f`

## Estrutura de Arquivos

Os seguintes arquivos são importantes para o deploy:

- `Procfile` - Comando de inicialização
- `railway.json` - Configuração do Railway
- `application-prod.yml` - Configuração de produção
- `pom.xml` - Dependências Maven

## Próximos Passos

Após o deploy bem-sucedido:

1. Configure um domínio customizado (opcional)
2. Configure monitoramento e alertas
3. Configure backups do banco de dados
4. Revise as configurações de segurança

