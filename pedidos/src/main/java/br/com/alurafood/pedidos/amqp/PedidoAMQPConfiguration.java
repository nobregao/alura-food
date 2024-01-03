package br.com.alurafood.pedidos.amqp;

import static br.com.alurafood.pedidos.util.RabbitMQUtil.EXCHANGE_PAGAMENTOS;
import static br.com.alurafood.pedidos.util.RabbitMQUtil.QUEUE_PEDIDOS;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoAMQPConfiguration {

	@Bean
	public Jackson2JsonMessageConverter setDefaultConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate set(ConnectionFactory connectionFactory,
			Jackson2JsonMessageConverter messageConverter){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}

	@Bean
	public Queue queuePedidoDetalhes(){
		return new Queue(QUEUE_PEDIDOS, false);
	}

	@Bean
	public FanoutExchange setFanoutExchange(){
		return new FanoutExchange(EXCHANGE_PAGAMENTOS);
	}

	@Bean
	public Binding bindPagamentoPedido(FanoutExchange fanoutExchange){
		return BindingBuilder
				.bind(queuePedidoDetalhes())
				.to(fanoutExchange);
	}

	@Bean
	public RabbitAdmin setRabbitAdmin(ConnectionFactory conn){
		return new RabbitAdmin(conn);
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin){
		return event -> rabbitAdmin.initialize();
	}

}
