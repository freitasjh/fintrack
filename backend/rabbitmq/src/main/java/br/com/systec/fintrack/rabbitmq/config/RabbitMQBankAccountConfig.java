package br.com.systec.fintrack.rabbitmq.config;

import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBankAccountConfig {

    @Bean
    DirectExchange directBankAccountExchange() {
        return new DirectExchange(RabbitMQConstants.FINANCIAL_EXCHANGE_BANK_ACCOUNT);
    }

    @Bean
    Queue createBankAccountNewAccountQueue() {
        return new Queue(RabbitMQConstants.NEW_ACCOUNT_BANK_ACCOUNT_QUEUE, true, false, true);
    }

    @Bean
    Queue queueBankAccountBalanceUpdate() {
        return new Queue(RabbitMQConstants.BANK_ACCOUNT_BALANCE_UPDATE, true, false, true);
    }

    @Bean
    Queue queueBankAccountNew() {
        return new Queue(RabbitMQConstants.BANK_ACCOUNT_NEW, true, false, true);
    }


    @Bean
    Binding bindingNewAccountReceive() {
        return BindingBuilder.bind(createBankAccountNewAccountQueue())
                .to(directBankAccountExchange()).with(RabbitMQConstants.ROUTING_KEY_NEW_ACCOUNT);
    }

    @Bean
    Binding bindingBankAccountBalanceUpdate() {
        return BindingBuilder.bind(queueBankAccountBalanceUpdate())
                .to(directBankAccountExchange()).with(RabbitMQConstants.ROUTING_KEY_UPDATE_BALANCE_ACCOUNT);
    }

    @Bean
    Binding bindingBankAccountNew() {
        return BindingBuilder.bind(queueBankAccountNew())
                .to(directBankAccountExchange()).with(RabbitMQConstants.ROUTING_KEY_NEW_BANK_ACCOUNT);
    }

}
