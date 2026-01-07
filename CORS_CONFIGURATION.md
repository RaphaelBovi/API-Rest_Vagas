# Configuração CORS - Backend API

## ✅ Configuração Implementada

O backend está configurado para aceitar requisições do frontend React em `https://raphaelvagas.com`.

### 1. Configuração Java (CorsConfig.java)

- ✅ Permite origens: `https://raphaelvagas.com` e `https://www.raphaelvagas.com`
- ✅ Permite métodos: GET, POST, PUT, DELETE, OPTIONS, PATCH
- ✅ Permite todos os headers (`*`)
- ✅ Credenciais habilitadas (`allowCredentials: true`)
- ✅ Headers expostos (`exposedHeaders: *`)
- ✅ Configurado para `/api/**` e `/**`

### 2. Configuração YAML (application.yml)

- ✅ Configuração CORS no `application.yml` base
- ✅ Configuração específica no `application-prod.yml` para produção
- ✅ Suporta variável de ambiente `CORS_ALLOWED_ORIGINS`

### 3. Endpoint de Health Check

- ✅ Criado endpoint `/api/health` para testar a conexão
- ✅ Acesse: `https://api-vagasraphael.com/api/health`

## 🔗 URLs Configuradas

### Frontend
- `https://raphaelvagas.com` ✅
- `https://www.raphaelvagas.com` ✅

### Backend API
- `https://api-vagasraphael.com/api` ✅

### Desenvolvimento Local
- `http://localhost:5173` ✅ (Vite)
- `http://localhost:3000` ✅ (React padrão)

## 🧪 Como Testar

### 1. Testar Health Check

```bash
curl https://api-vagasraphael.com/api/health
```

Resposta esperada:
```json
{
  "status": "UP",
  "message": "API está funcionando corretamente",
  "timestamp": 1234567890
}
```

### 2. Testar CORS do Frontend

No console do navegador (F12) ao acessar `https://raphaelvagas.com`:

```javascript
fetch('https://api-vagasraphael.com/api/health', {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
  },
  credentials: 'include'
})
.then(response => response.json())
.then(data => console.log('Sucesso:', data))
.catch(error => console.error('Erro:', error));
```

### 3. Testar Endpoint de Currículos

```javascript
fetch('https://api-vagasraphael.com/api/curriculos', {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
  },
  credentials: 'include'
})
.then(response => response.json())
.then(data => console.log('Currículos:', data))
.catch(error => console.error('Erro:', error));
```

## ⚙️ Variáveis de Ambiente no Railway

No Railway, certifique-se de ter:

```json
{
  "CORS_ALLOWED_ORIGINS": "https://raphaelvagas.com,https://www.raphaelvagas.com",
  "SPRING_PROFILES_ACTIVE": "prod"
}
```

## 🔍 Troubleshooting

### Erro: "Access to fetch at '...' from origin '...' has been blocked by CORS policy"

**Soluções:**
1. Verifique se `CORS_ALLOWED_ORIGINS` está configurado no Railway
2. Verifique se o domínio do frontend está na lista de origens permitidas
3. Verifique se `SPRING_PROFILES_ACTIVE=prod` está configurado
4. Verifique os logs do backend no Railway para erros

### Erro: "Preflight request doesn't pass access control check"

**Soluções:**
1. Certifique-se de que `OPTIONS` está nos métodos permitidos (já está)
2. Verifique se `allowCredentials` está configurado (já está)
3. Verifique se os headers estão permitidos (já está como `*`)

### API não responde

**Soluções:**
1. Verifique se a API está rodando: `https://api-vagasraphael.com/api/health`
2. Verifique os logs do Railway
3. Verifique se o domínio da API está configurado corretamente

## 📝 Checklist de Verificação

- [x] CorsConfig.java criado e configurado
- [x] application.yml com configuração CORS
- [x] application-prod.yml com configuração CORS
- [x] HealthController criado para testes
- [x] Variáveis de ambiente configuradas no railway-variables.json
- [x] Domínios permitidos: raphaelvagas.com e www.raphaelvagas.com
- [x] Métodos HTTP permitidos: GET, POST, PUT, DELETE, OPTIONS, PATCH
- [x] Credenciais habilitadas
- [x] Headers permitidos: todos

## 🚀 Próximos Passos

1. Faça deploy do backend no Railway
2. Verifique se o endpoint `/api/health` está acessível
3. Teste a conexão do frontend com a API
4. Verifique o console do navegador para erros de CORS
5. Se houver erros, verifique os logs do Railway

