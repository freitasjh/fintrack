package br.com.systec.fintrack.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreditCardInvoiceJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(CreditCardInvoiceJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Iniciado desenvolvimento do job");
    }
}
