package br.com.systec.controle.financeiro.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String NEW_ACCOUNT_CATEGORY_QUEUE = "new-account-category";
    public static final String NEW_ACCOUNT_BANK_ACCOUNT_QUEUE = "new-account-bank-account";
    public static final String FINANCIAL_EXCHANGE = "fx-financial";
    private final CachingConnectionFactory connectionFactory;

    public RabbitMQConfig(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    Queue createCategoryNewAccountQueue() {
        return new Queue(NEW_ACCOUNT_CATEGORY_QUEUE, true, false, true);
    }

    @Bean
    Queue createBankAccountNewAccountQueue() {
        return new Queue(NEW_ACCOUNT_BANK_ACCOUNT_QUEUE, true, false, true);
    }

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(FINANCIAL_EXCHANGE);
    }

    @Bean
    Binding createBindingCategory() {
        return BindingBuilder.bind(createCategoryNewAccountQueue()).to(fanoutExchange());
    }

    @Bean
    Binding createBindingBankAccount() {
        return BindingBuilder.bind(createBankAccountNewAccountQueue()).to(fanoutExchange());
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
