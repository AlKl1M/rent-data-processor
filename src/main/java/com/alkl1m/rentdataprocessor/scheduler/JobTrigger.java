package com.alkl1m.rentdataprocessor.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JobTrigger {

    private final JobLauncher jobLauncher;
    private final Job job;


    public JobTrigger(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(fixedRateString = "${jobtrigger.fixedRate}")
    public void launchJobCron() {
        try {
            var jobParameters = new JobParametersBuilder();
            jobParameters.addDate("uniqueness", new Date());
            JobExecution run = this.jobLauncher.run(job, jobParameters.toJobParameters());
            log.info("Job executed successfully with status: {}", run.getStatus());
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            log.error("Job execution failed", e);
        }
    }
}