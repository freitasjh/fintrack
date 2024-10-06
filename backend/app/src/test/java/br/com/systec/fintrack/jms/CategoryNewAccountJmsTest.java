package br.com.systec.fintrack.jms;

import br.com.systec.fintrack.integration.MysqlTestIT;
import br.com.systec.fintrack.integration.RabbitMQContainerIT;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class CategoryNewAccountJmsTest extends MysqlTestIT implements RabbitMQContainerIT {
    private static final Logger log = LoggerFactory.getLogger(CategoryNewAccountJmsTest.class);
    @Autowired
    RabbitTemplate rabbitTemplate;


    @Test
    void teste(CapturedOutput output) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.FINANCIAL_EXCHANGE, "",
                "1"
        );

        log.info("Output {}", output.getOut());
    }
}
