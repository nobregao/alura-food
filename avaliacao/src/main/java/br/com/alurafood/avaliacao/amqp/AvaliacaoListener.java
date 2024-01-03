package br.com.alurafood.avaliacao.amqp;

import static br.com.alurafood.avaliacao.util.RabbitMQUtil.QUEUE_AVALIACAO;

import br.com.alurafood.avaliacao.dto.PagamentoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoListener {
    @RabbitListener(queues = QUEUE_AVALIACAO)
    public void recebeMensagem(@Payload PagamentoDto pagamento) {
        System.out.println(pagamento.getId());
        System.out.println(pagamento.getNumero());

        if (pagamento.getNumero().equals("0000")) {
            throw new RuntimeException("não consegui processar");
        }

        String mensagem = """
                Necessário criar registro de avaliação para o pedido: %s 
                Id do pagamento: %s
                Nome do cliente: %s
                Valor R$: %s
                Status: %s 
                """.formatted(pagamento.getPedidoId(),
                pagamento.getId(),
                pagamento.getNome(),
                pagamento.getValor(),
                pagamento.getStatus());

        System.out.println(mensagem);
    }
}
