package br.com.systec.fintrack.quarz.impl.jms;

import br.com.systec.fintrack.quartz.service.JobService;
import br.com.systec.fintrack.quartz.vo.JobVO;
import br.com.systec.fintrack.rabbitmq.utils.RabbitMQConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class JobNewAccountJms {
    private static final Logger log = LoggerFactory.getLogger(JobNewAccountJms.class);
    @Autowired
    private JobService service;

    @RabbitListener(queues = RabbitMQConstants.JOB_NEW_ACCOUNT)
    public void insertJobs(@Payload Message message) {
        try {
            String payload = String.valueOf(message.getPayload());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream("json/jobs-insert.json")) {
                List<JobVO> listOfJob = objectMapper.readValue(inputStream, new TypeReference<List<JobVO>>() {
                });

                for (JobVO job : listOfJob) {
                    job.setTenantId(Long.parseLong(payload));
                    service.save(job);
                    log.info("BankAccount {}", job.getName());
                }
            }
        }catch (Exception e){
            log.error("Ocorreu um erro ao tentar inserir os jobs default", e);
        }

    }
}
