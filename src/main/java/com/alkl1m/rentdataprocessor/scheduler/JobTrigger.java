package com.alkl1m.rentdataprocessor.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Запускает задачи по расписанию.
 *
 * @author AlKl1M
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JobTrigger {

    private final Job job;
    private final JobLauncher jobLauncher;

    /**
     * Метод запускает задание по расписанию.
     */
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
