# alura-food

Projeto desenvolvido em Spring utilizando Eureka e RabbitMQ com objetivo de praticar comunicação assíncrona.

## Arquitetura

Server:
- servidor de registro para os microsserviços.

Microsserviços:
- pagamentos
- pedidos
- avaliacao

Exchange:
- pagamentos.ex

Filas:
- pagamentos.detalhes-pedidos
- pagamentos.detalhes-avaliacao
- pagamentos.detalhes-avaliacao.dlq (dead letter queue)

![project-architecture](architecture.png)

## Endpoint

**POST** `/pagamentos-ms/pagamentos` Cadastra pagamento

### Curl
```bash
curl --request POST \
  --url http://localhost:8082/pagamentos-ms/pagamentos \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/8.4.2' \
  --data '{
        "valor": 1300.00,
        "nome": "renatão",
        "numero": "0001",
        "expiracao": "10/2028",
        "codigo": 123,
        "status": "CRIADO",
        "formaDePagamentoId": 1,
        "pedidoId": 1
}'
```
### Response
```json
{
	"id": 1,
	"valor": 1300.00,
	"nome": "renatão",
	"numero": "0001",
	"expiracao": "10/2028",
	"codigo": "123",
	"status": "CRIADO",
	"pedidoId": 1,
	"formaDePagamentoId": 1
}
```

## Como Usar

- Adicione a imagem e suba o container docker do RabbitMQ
```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
```
- Suba os microsserviços *server*, *gateway*, *pagamentos*, *avaliacao* e *pedido*

- Consulte os serviços registrados no Eureka em http://localhost:8081/

- Consulte as filas do RabbitMQ em http://localhost:15672/
     - Usuario e Senha é **guest**

## Links
- [RabbitMQ](https://www.rabbitmq.com/)
- [Formação Java e Microsserviços com Spring e RabbitMQ](https://cursos.alura.com.br/formacao-java-microsservicos)   