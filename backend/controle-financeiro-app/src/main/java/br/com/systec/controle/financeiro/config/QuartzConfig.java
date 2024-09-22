package br.com.systec.controle.financeiro.config;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource quartzDataSource, QuartzProperties quartzProperties) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setDataSource(quartzDataSource);
        return factory;
    }

    @Bean
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory(beanFactory);
    }
}