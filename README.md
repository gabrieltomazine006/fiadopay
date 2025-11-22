FiaDoPay – API de Pagamentos (Versão Atualizada)

Esta aplicação é uma API completa para criação de usuários, merchants,
emissão de tokens, geração de pagamentos, processamento assíncrono e
webhooks.

------------------------------------------------------------------------

🚀 Fluxo Completo

------------------------------------------------------------------------

1. Criar Usuário

Retorna Bearer Token

    POST /users
    Headers:
      Content-Type: application/json

    Body:
    {
      "email": "${EMAIL}",
      "password": "${PASSWORD}"
    }

------------------------------------------------------------------------

2. Criar Merchant

    POST /merchant
    Headers:
      Authorization: Bearer ${USER_TOKEN}
      Content-Type: application/json

    Body:
    {
        "name": "MinhaLoja xY",
        "webhookUrl": "http://localhost:8081/webhooks/payments",
        "interest": 3
    }

Retorno:

    {
      "clientId": "...",
      "secretKey": "..."
    }

------------------------------------------------------------------------

3. Obter Token BASIC

Gera o token utilizados nos pagamentos.

    POST /merchant/obterToken
    Headers:
      Content-Type: application/json

    Body:
    {
      "clientId": "${CLIENT_ID}",
      "secretKey": "${SECRET_KEY}"
    }

Retorno:

    {
      "token": "Basic ZGFkYXNkYXNkYXNk..."
    }

------------------------------------------------------------------------

💳 4. Criar Pagamento

    POST /payments
    Headers:
      Authorization: Basic {{bToken}}
      Idempotency-Key: 123e4567-e89b-12d3-a456-426614174000
      Content-Type: application/json

    Body:
    {
      "method": "CARD",
      "currency": "BRL",
      "amount": 19990.50,
      "metadataOrderId": "order-123",
      "details": {
        "installments": 3
      }
    }

Retorno:

    {
      "paymentId": "...",
      "status": "PROCESSING",
      "amount": 19990.50,
      "currency": "BRL",
      "method": "CARD"
    }

------------------------------------------------------------------------

🔎 5. Consultar Pagamento

    GET /payments/{paymentId}
    Headers:
      Authorization: Basic {{bToken}}

------------------------------------------------------------------------

📡 Webhooks

O merchant recebe eventos como:

-   PAYMENT_PROCESSING
-   PAYMENT_APPROVED
-   PAYMENT_FAILED

Sempre enviados ao webhookUrl.

------------------------------------------------------------------------

🧵 Threads Assíncronas

    fiadopay.webhook-threads=8

Utilizadas para processar eventos sem bloquear o Tomcat.

------------------------------------------------------------------------

📝 Logs e Métricas

-   Logback configurado
-   Métricas de tentativas de webhook
-   Logs estruturados por Payment e Merchant

------------------------------------------------------------------------

🧬 Entidades

User, Merchant, Payment, Webhook e Delivery.

------------------------------------------------------------------------

✔ Final

Fluxo completo:

1.  Criar User → token Bearer
2.  Criar Merchant → clientId + secretKey
3.  Obter Basic Token
4.  Criar Pagamento
5.  Consultar Pagamento
6.  Receber Webhooks

Tudo pronto!
