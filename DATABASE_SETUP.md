# Configuração do Banco de Dados PostgreSQL no Railway

## Credenciais do Banco de Dados

Se você está usando um banco PostgreSQL existente no Railway:

- **Host:** `postgres-production-e12e.up.railway.app`
- **Porta:** `5432` (padrão PostgreSQL)
- **Database:** `railway` (padrão do Railway)
- **Username:** `postgres`
- **Password:** `RyzUTLHdHjIxlxlThzJbMsIxrPviUJlA`

## URL de Conexão JDBC

```
jdbc:postgresql://postgres-production-e12e.up.railway.app:5432/railway
```

## Configuração no Railway

### Opção 1: Variáveis de Ambiente (Recomendado)

No dashboard do Railway, configure as seguintes variáveis de ambiente:

```bash
DATABASE_URL=jdbc:postgresql://postgres-production-e12e.up.railway.app:5432/railway
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=RyzUTLHdHjIxlxlThzJbMsIxrPviUJlA
SPRING_PROFILES_ACTIVE=prod
```

### Opção 2: Conectar Serviço PostgreSQL

Se você adicionar um novo serviço PostgreSQL no Railway:
1. Clique em "+ New" → "Database" → "Add PostgreSQL"
2. O Railway criará automaticamente as variáveis de ambiente
3. Conecte o serviço à sua aplicação

## Criação Automática de Tabelas

✅ **As tabelas serão criadas automaticamente!**

O projeto está configurado com `ddl-auto: update` no `application-prod.yml`, o que significa que:

- ✅ No primeiro deploy, o JPA criará todas as tabelas automaticamente
- ✅ Quando você modificar entidades, o schema será atualizado automaticamente
- ✅ Não é necessário criar tabelas manualmente

### Tabelas que serão criadas:

1. **curriculos** - Tabela principal de currículos
2. **cursos_complementares** - Cursos complementares (FK para curriculos)
3. **idiomas** - Idiomas (FK para curriculos)
4. **skills** - Skills (FK para curriculos)

## Verificação

Após o deploy, você pode verificar se as tabelas foram criadas:

1. Acesse o dashboard do PostgreSQL no Railway
2. Use o terminal SQL ou ferramenta de gerenciamento
3. Execute: `\dt` (no psql) ou `SHOW TABLES;` para listar as tabelas

## Troubleshooting

### Erro: "Table does not exist"

- Verifique se `SPRING_PROFILES_ACTIVE=prod` está configurado
- Confirme que `ddl-auto: update` está no `application-prod.yml`
- Verifique os logs do Railway para erros de conexão

### Erro de Conexão

- Verifique se as credenciais estão corretas
- Confirme que o host está acessível
- Verifique se a porta 5432 está aberta

### Tabelas não foram criadas

- Verifique os logs da aplicação no Railway
- Confirme que o perfil `prod` está ativo
- Verifique se há erros de permissão no banco de dados






