package br.com.systec.controle.financeiro.jms;

import br.com.systec.controle.financeiro.config.RabbitMQConfig;
import br.com.systec.controle.financeiro.integration.MysqlTestIT;
import br.com.systec.controle.financeiro.integration.RabbitMQContainerIT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.concurrent.Callable;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class NewAccountJmsTest extends MysqlTestIT implements RabbitMQContainerIT {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Test
    void teste(CapturedOutput output) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.FINANCIAL_EXCHANGE,
                "1"
        );

        System.out.println(output.getOut());
    }

    private Callable<Boolean> paymentUpdated(CapturedOutput output) {
        return () -> output.getOut().contains("Payment updated");
    }
}
