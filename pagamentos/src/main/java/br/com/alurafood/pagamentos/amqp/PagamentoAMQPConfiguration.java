package br.com.alurafood.pagamentos.amqp;

import static br.com.alurafood.pagamentos.util.RabbitMQUtil.EXCHANGE_PAGAMENTOS;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoAMQPConfiguration {

	@Bean
	public RabbitAdmin setRabbitAdmin(ConnectionFactory conn){
		return new RabbitAdmin(conn);
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin){
		return event -> rabbitAdmin.initialize();
	}

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
	public FanoutExchange setFanoutExchange(){
		return new FanoutExchange(EXCHANGE_PAGAMENTOS);
	}

}
