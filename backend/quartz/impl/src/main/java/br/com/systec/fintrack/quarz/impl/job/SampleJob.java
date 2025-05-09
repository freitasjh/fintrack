package br.com.systec.fintrack.quarz.impl.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executando job de exemplo: " + context.getJobDetail().getKey());
    }
}