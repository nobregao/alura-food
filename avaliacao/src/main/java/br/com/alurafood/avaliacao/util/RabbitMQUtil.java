package br.com.alurafood.avaliacao.util;

public class RabbitMQUtil {

	public static final String EXCHANGE_PAGAMENTOS = "pagamentos.ex";
	public static final String EXCHANGE_DEADLETTER = "pagamentos.dlx";
	public static final String QUEUE_AVALIACAO = "pagamentos.detalhes-avaliacao";
	public static final String QUEUE_AVALIACAO_DEAD_LETTER = "pagamentos.detalhes-avaliacao-dlq";


}
