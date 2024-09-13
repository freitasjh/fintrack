package br.com.systec.controle.financeiro.administrator.category.jms;

import br.com.systec.controle.financeiro.administrator.category.model.Category;
import br.com.systec.controle.financeiro.administrator.category.service.CategoryService;
import br.com.systec.controle.financeiro.config.RabbitMQConfig;
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

@Component
public class CategoryNewAccountJMS {
    private static final Logger log = LoggerFactory.getLogger(CategoryNewAccountJMS.class);

    @Autowired
    private CategoryService categoryService;

    @RabbitListener(queues = RabbitMQConfig.NEW_ACCOUNT_CATEGORY_QUEUE)
    public void createCategoryForTenant(@Payload Message message) {
        try {
            String payload = String.valueOf(message.getPayload());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try(InputStream inputStream = classLoader.getResourceAsStream("json/category-insert.json")) {
                ListCategory listCategory = objectMapper.readValue(inputStream, ListCategory.class);

                for (Category category : listCategory.getCategories()) {
                    category.setTenantId(Long.parseLong(payload));
                    categoryService.save(category);
                    log.info("Categoria {}", category.toString());
                }
            }

        } catch (Exception e) {
            log.error("Erro ao tentar criar as categorias para a nova conta", e);
        }
    }
}
