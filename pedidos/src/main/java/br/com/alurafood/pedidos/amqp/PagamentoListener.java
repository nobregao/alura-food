package br.com.alurafood.pedidos.amqp;

import static br.com.alurafood.pedidos.util.RabbitMQUtil.QUEUE_PEDIDOS;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.alurafood.pedidos.dto.PagamentoDTO;

@Component
public class PagamentoListener {

	@RabbitListener(queues = QUEUE_PEDIDOS)
	public void listenerMessage(PagamentoDTO pagamentoDTO){

		String mensagem = """
   				Dados do pagamento: %s
   				Número do pedido: %s
   				Valor R$: %s
   				Status: %s
				""".formatted(
						pagamentoDTO.getId(),
						pagamentoDTO.getPedidoId(),
						pagamentoDTO.getValor(),
						pagamentoDTO.getStatus());

		System.out.println(String.format("Recebi a mensagem: \n%s", mensagem));
	}
}
