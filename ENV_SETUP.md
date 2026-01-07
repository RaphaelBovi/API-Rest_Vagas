# Configuração de Variáveis de Ambiente

Este documento descreve as variáveis de ambiente necessárias para o projeto.

## Variáveis Obrigatórias

### Banco de Dados PostgreSQL

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/empregar_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres
```

**Para Railway (Produção):**
- O Railway fornece automaticamente essas variáveis quando você conecta um serviço PostgreSQL
- As variáveis serão nomeadas como `DATABASE_URL`, `PGUSER`, `PGPASSWORD`, etc.
- **OU** configure manualmente se usar um banco existente:
  ```bash
  DATABASE_URL=jdbc:postgresql://postgres-production-e12e.up.railway.app:5432/railway
  DATABASE_USERNAME=postgres
  DATABASE_PASSWORD=RyzUTLHdHjIxlxlThzJbMsIxrPviUJlA
  ```
- Configure no dashboard do Railway: Settings → Variables
- **Importante:** O JPA está configurado com `ddl-auto: update`, então as tabelas serão criadas automaticamente no primeiro deploy

### API Adzuna

```bash
ADZUNA_APP_ID=7723771c
ADZUNA_APP_KEY=a4e84f857e72f1e171d605c4ef2c275f
ADZUNA_TIMEOUT=5000
```

**Nota:** As credenciais já estão configuradas como valores padrão no `application.yml`. Você pode sobrescrever usando variáveis de ambiente se necessário.

## Variáveis Opcionais

```bash
# Perfil Spring (dev ou prod)
SPRING_PROFILES_ACTIVE=dev

# Configuração JPA
DDL_AUTO=update  # update, validate, create, create-drop, none
SHOW_SQL=false   # true para ver SQL no console

# Porta do servidor
PORT=8080
```

## Configuração Local

1. Crie um arquivo `.env` na raiz do projeto (não será commitado)
2. Copie as variáveis acima e preencha com seus valores
3. Para desenvolvimento local, use:
   ```bash
   SPRING_PROFILES_ACTIVE=dev
   ```

## Configuração no Railway

1. Acesse o dashboard do Railway
2. Vá em Settings → Variables
3. Adicione as seguintes variáveis:
   - `SPRING_PROFILES_ACTIVE=prod`
   - `ADZUNA_APP_ID` (seu app ID da Adzuna)
   - `ADZUNA_APP_KEY` (sua app key da Adzuna)
4. Conecte um serviço PostgreSQL - o Railway criará automaticamente:
   - `DATABASE_URL`
   - `PGUSER` ou `DATABASE_USERNAME`
   - `PGPASSWORD` ou `DATABASE_PASSWORD`

## Segurança

⚠️ **IMPORTANTE:**
- NUNCA commite arquivos `.env` com valores reais
- Use variáveis de ambiente na plataforma de deploy
- O arquivo `.env.example` serve apenas como template

