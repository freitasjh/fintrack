package br.com.systec.controle.financeiro.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
    public static final String BANK_ACCOUNT_BALANCE_UPDATE = "bank-account-balance-update";
    public static final String ROUTING_KEY_NEW_ACCOUNT = "rt-newaccount";
    public static final String ROUTING_KEY_NEW_BALANCE_ACCOUNT = "rt-newbalance-account";
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
    Queue queueBankAccountBalanceUpdate() {
        return new Queue(BANK_ACCOUNT_BALANCE_UPDATE, true, false, true);
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(FINANCIAL_EXCHANGE);
    }

    @Bean
    Binding bindingNewAccountCategory() {
        return BindingBuilder.bind(createCategoryNewAccountQueue()).
                to(directExchange()).with(ROUTING_KEY_NEW_ACCOUNT);
    }

    @Bean
    Binding bindingNewAccountReceive() {
        return BindingBuilder.bind(createBankAccountNewAccountQueue())
                .to(directExchange()).with(ROUTING_KEY_NEW_ACCOUNT);
    }

    @Bean
    Binding bindingBankAccountBalanceUpdate() {
        return BindingBuilder.bind(queueBankAccountBalanceUpdate())
                .to(directExchange()).with(ROUTING_KEY_NEW_BALANCE_ACCOUNT);
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
