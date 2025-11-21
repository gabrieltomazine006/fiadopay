FiaDoPay – API de Pagamentos

Este projeto é uma API de pagamentos construída em Java + Spring Boot,
utilizando autenticação segura via Spring Security, emissão de tokens
(Bearer e Basic), processamento assíncrono, webhooks, entregas de
eventos e sistema completo de merchants e pagamentos.

------------------------------------------------------------------------

🚀 Fluxo Completo da Plataforma

1. Criar Usuário

O cliente inicia criando um usuário com e-mail e senha.
Após o cadastro, o sistema retorna um token Bearer.

Exemplo de Requisição (via ambiente):

    POST /users
    Authorization: none
    BODY:
    {
      "email": "${EMAIL}",
      "password": "${PASSWORD}"
    }

Resposta:

    {
      "token": "Bearer eyJhbGciOi..."
    }

------------------------------------------------------------------------

2. Criar Merchant

Com o token Bearer do usuário, ele cria um Merchant.

    POST /merchant
    Authorization: Bearer ${USER_TOKEN}
    BODY:
    {
      "name": "${MERCHANT_NAME}"
    }

Resposta:

    {
      "clientId": "a81ba72c-f7a8-4e3d-9c41-87af",
      "secretKey": "41a0d22-f1ce9b892"
    }

------------------------------------------------------------------------

3. Obter Token Basic do Merchant

O Merchant utiliza suas chaves (clientId e secretKey) para gerar um
token Basic.

    POST /merchant/obterToken
    BODY:
    {
      "clientId": "${CLIENT_ID}",
      "secretKey": "${SECRET_KEY}"
    }

Retorno:

    {
      "token": "Basic ZGFza..."
    }

Esse token Basic será usado para gerar pagamentos e consultar
transações.

------------------------------------------------------------------------

💳 4. Criar Pagamento

    POST /payments
    Authorization: Basic ${MERCHANT_TOKEN}
    BODY:
    {
      "amount": 150.00,
      "description": "Pedido #5822",
      "paymentMethod": "PIX",
      "customer": {
        "name": "André Luiz",
        "email": "andre@email.com"
      }
    }

Resposta:

    {
      "paymentId": "e12f98b7-5b2d-4dd8-b7a1-4f8",
      "status": "PROCESSING",
      "createdAt": "...",
      "merchantId": "...",
      "amount": 150.00
    }

------------------------------------------------------------------------

🔎 5. Consultar Pagamento

    GET /payments/{id}
    Authorization: Basic ${MERCHANT_TOKEN}

------------------------------------------------------------------------

📡 Webhooks e Entregas (Delivery)

A aplicação possui:

-   Webhook: eventos enviados ao sistema do cliente.
-   Delivery Service: reentregas automáticas e logs de tentativas.
-   WebhookEventFactory: cria eventos para cada mudança de estado do
    pagamento.

------------------------------------------------------------------------

🧵 Threads / Processamento Assíncrono

A aplicação utiliza uma execução paralela configurada:

    fiadopay.webhook-threads=8

Se o valor não existir no .env ou variáveis de ambiente, o sistema usa 8
como padrão.

Esse sistema permite processar múltiplos pagamentos simultaneamente,
evitando bloqueios do Tomcat.

------------------------------------------------------------------------

📝 Logs e Métricas

A aplicação contém:

-   Logback configurado
-   Logs estruturados por contexto (user, merchant, payment)
-   Métricas do processamento assíncrono
-   Rastreamento de webhooks e entregas

------------------------------------------------------------------------

🧬 Entidades Principais

User

-   id
-   email
-   password
-   merchants (lista)

Merchant

-   id
-   name
-   clientId
-   secretKey
-   payments
-   webhooks

Payment

-   id
-   amount
-   status
-   merchantId
-   eventos de webhook

Webhook

-   id
-   event
-   url
-   merchant

Delivery

-   id
-   webhookId
-   attempt
-   status

------------------------------------------------------------------------

🧪 Variáveis de Ambiente (Exemplos)

    EMAIL="andre@email.com"
    PASSWORD="123456"
    MERCHANT_NAME="Loja FiaDoPay"
    CLIENT_ID="..."
    SECRET_KEY="..."
    USER_TOKEN="..."
    MERCHANT_TOKEN="..."

------------------------------------------------------------------------

📦 Resumo

A aplicação fornece um fluxo completo:

1.  Criar user → recebe Bearer
2.  Criar merchant → recebe clientId + secretKey
3.  Obter token Basic
4.  Criar pagamento
5.  Consultar pagamento
6.  Receber eventos via webhook
7.  Monitorar tentativas de entrega

------------------------------------------------------------------------

✔ Final

Esse README resume todo o fluxo e explica a função de cada parte da
aplicação.
