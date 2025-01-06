package com.alkl1m.rentdataprocessor.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomJobExecutionListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getStepExecutions()
                .stream()
                .findFirst()
                .ifPresent(stepExecution -> {
                    long writeCount = stepExecution.getWriteCount();
                    System.out.println(String.format("%s lines has been written", writeCount));
                });
    }
}
