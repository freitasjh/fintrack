package br.com.systec.fintrack.quartz.service;

import br.com.systec.fintrack.quartz.vo.JobVO;
import org.quartz.Job;
import org.quartz.SchedulerException;

import javax.print.attribute.standard.JobImpressionsCompleted;

public interface JobService {
    void scheduleJob(String jobName, String groupName, Class<? extends Job> jobClass,
                            String cronExpression, Long tenantId) throws SchedulerException;
    void save(JobVO jobVO) throws Exception;
    void deleteJob(String jobName, String groupName) throws SchedulerException;
}
