# Variáveis de Ambiente do Railway

Este arquivo contém todas as variáveis de ambiente necessárias para o projeto no Railway.

## Como usar no Railway

1. Acesse o dashboard do Railway
2. Vá em seu projeto → Settings → Variables
3. Clique em "Raw Editor" (modo JSON)
4. Cole o conteúdo do arquivo `railway-variables.json`
5. Salve as alterações

## Variáveis Configuradas

### Banco de Dados PostgreSQL
- `DATABASE_URL` - URL de conexão JDBC completa
- `DATABASE_USERNAME` - Username do banco (postgres)
- `DATABASE_PASSWORD` - Senha do banco
- Variáveis PostgreSQL padrão do Railway (PGUSER, PGPASSWORD, etc.)

### Spring Boot
- `SPRING_PROFILES_ACTIVE=prod` - Ativa o perfil de produção
- `DDL_AUTO=update` - Cria/atualiza tabelas automaticamente
- `SHOW_SQL=false` - Desabilita logs SQL em produção

### API Adzuna
- `ADZUNA_APP_ID=7723771c` - ID da aplicação Adzuna
- `ADZUNA_APP_KEY=a4e84f857e72f1e171d605c4ef2c275f` - Chave da aplicação Adzuna
- `ADZUNA_TIMEOUT=5000` - Timeout em milissegundos

## Formato JDBC para DATABASE_URL

**Importante:** O Spring Boot precisa da URL no formato JDBC. Se o Railway fornecer apenas no formato `postgresql://`, você pode precisar ajustar:

```json
"DATABASE_URL": "jdbc:postgresql://${{RAILWAY_PRIVATE_DOMAIN}}:5432/${{PGDATABASE}}"
```

Ou use a variável completa:
```json
"DATABASE_URL": "jdbc:postgresql://postgres-production-e12e.up.railway.app:5432/railway"
```

## Notas

- As variáveis com `${{...}}` são referências a outras variáveis do Railway
- `DATABASE_USERNAME` e `DATABASE_PASSWORD` são necessárias para o Spring Boot
- O perfil `prod` está ativo para usar `application-prod.yml`
- `DDL_AUTO=update` garante que as tabelas sejam criadas automaticamente






