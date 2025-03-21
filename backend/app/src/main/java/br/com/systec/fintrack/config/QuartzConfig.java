package br.com.systec.fintrack.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Bean
    @Primary
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

    @Bean
    public Scheduler registerQuartzMetrics(Scheduler scheduler, MeterRegistry meterRegistry) throws SchedulerException {
        meterRegistry.gauge("quartz.jobs.executed", scheduler, s -> {
            try {
                return s.getMetaData().getNumberOfJobsExecuted();
            } catch (SchedulerException e) {
                return 0;
            }
        });

        meterRegistry.gauge("quartz.triggers.count", scheduler, s -> {
            try {
                return s.getTriggerGroupNames().size();
            } catch (SchedulerException e) {
                return 0;
            }
        });

        meterRegistry.gauge("quartz.executing.jobs", scheduler, s -> {
            try {
                return s.getCurrentlyExecutingJobs().size();
            } catch (SchedulerException e) {
                return 0;
            }
        });

        meterRegistry.gauge("quartz.jobs.group.count", scheduler, s -> {
            try {
                return s.getJobGroupNames().size();
            } catch (SchedulerException e) {
                return 0;
            }
        });

        return scheduler;
    }
}