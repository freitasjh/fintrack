package br.com.systec.fintrack;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = AbstractIT.DockerMysqlDataSourceInitializer.class)
@Testcontainers
public class AbstractIT {

    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.28");

    static {
        mySQLContainer.start();
    }

    public static class DockerMysqlDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            );
        }
    }
}