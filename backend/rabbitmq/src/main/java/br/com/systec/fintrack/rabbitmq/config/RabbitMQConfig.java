package br.com.systec.fintrack.rabbitmq.config;

import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //TODO: implementar DLQ na logica de mensageria
    private final CachingConnectionFactory connectionFactory;

    public RabbitMQConfig(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    Queue createCategoryNewAccountQueue() {
        return new Queue(RabbitMQConstants.NEW_ACCOUNT_CATEGORY_QUEUE, true, false, true);
    }

    @Bean
    Queue queueAccountPayment() {
        return new Queue(RabbitMQConstants.ACCOUNT_PAYMENT, true, false, true);
    }

    @Bean
    Queue queueJobNewAccount() { return new Queue(RabbitMQConstants.JOB_NEW_ACCOUNT, true, false, true); }

    @Bean
    Queue queueJobCreditCard() { return new Queue(RabbitMQConstants.CREDIT_CARD_JOB, true, false, true); }

    @Bean
    Queue queueRecurringJob(){ return new Queue(RabbitMQConstants.RECURRING_FINANCIAL_JOB, true, false, true); }

    @Bean
    Queue queueCreditCardNewInvoice(){ return new Queue(RabbitMQConstants.CREDIT_CARD_NEW_INVOICE, true,false,true); }

    @Bean
    Queue queueCreditCardQPending(){ return new Queue(RabbitMQConstants.CREDIT_CARD_INVOICE_PENDING, true,false,true); }

    @Bean
    Queue queueNotification(){ return new Queue(RabbitMQConstants.NOTIFICATION, true,false,true); }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(RabbitMQConstants.FINANCIAL_EXCHANGE);
    }

    @Bean
    Binding bindingNewAccountCategory() {
        return BindingBuilder.bind(createCategoryNewAccountQueue()).
                to(directExchange()).with(RabbitMQConstants.ROUTING_KEY_NEW_ACCOUNT);
    }

    @Bean
    Binding bindingJobNewAccount() {
        return BindingBuilder.bind(queueJobNewAccount())
                .to(directExchange()).with(RabbitMQConstants.ROUTING_KEY_NEW_ACCOUNT);
    }

    @Bean
    Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
