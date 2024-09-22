package br.com.systec.fintrack.quartz.api.v1;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.quartz.SampleJob;
import br.com.systec.fintrack.quartz.service.JobService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs")
@SecurityRequirement(name = "Authorization")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/add")
    public String addJob(@RequestParam String jobName,
                         @RequestParam String groupName,
                         @RequestParam String cronExpression) {
        try {
            jobService.scheduleJob(jobName, groupName, SampleJob.class, cronExpression,
                    TenantContext.getTenant());
            return "Job cadastrado com sucesso!";
        } catch (SchedulerException e) {
            return "Erro ao cadastrar o job: " + e.getMessage();
        }
    }

    @DeleteMapping("/remove")
    public String removeJob(@RequestParam String jobName, @RequestParam String groupName) {
        try {
            jobService.deleteJob(jobName, groupName);
            return "Job removido com sucesso!";
        } catch (SchedulerException e) {
            return "Erro ao remover o job: " + e.getMessage();
        }
    }
}