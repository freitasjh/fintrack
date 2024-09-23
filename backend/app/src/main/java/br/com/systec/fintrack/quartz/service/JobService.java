package br.com.systec.fintrack.quartz.service;

import br.com.systec.fintrack.quartz.model.JobVO;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private Scheduler scheduler;

    public void scheduleJob(String jobName, String groupName, Class<? extends Job> jobClass,
                            String cronExpression, Long tenantId) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, groupName)
                .usingJobData("tenantId", tenantId)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger", groupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void save(JobVO jobVO) throws Exception {
        Class jobClass = Class.forName(jobVO.getClassName());

        JobBuilder jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobVO.getName(), jobVO.getGroup());

        if (jobVO.getJobDataMap() == null) {
            jobDetail.usingJobData("tenantId", jobVO.getTenantId());
        } else {
            jobDetail.setJobData(jobVO.getJobDataMap());
        }

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobVO.getName() + "Trigger", jobVO.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(jobVO.getCron()))
                .build();

        scheduler.scheduleJob(jobDetail.build(), trigger);

    }

    public void deleteJob(String jobName, String groupName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, groupName);

        if(scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }
}