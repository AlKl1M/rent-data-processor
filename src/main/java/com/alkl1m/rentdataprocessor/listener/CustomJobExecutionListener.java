package com.alkl1m.rentdataprocessor.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Кастомный слушатель выполнения задания.
 *
 * @author AlKl1M
 */
@Slf4j
@Component
public class CustomJobExecutionListener implements JobExecutionListener {

    /**
     * Метод вызывается после завершения выполнения задания.
     * Логирует количество записанных строк в первом шаге выполнения задания.
     *
     * @param jobExecution объект, содержащий информацию о выполнении задания
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getStepExecutions()
                .stream()
                .findFirst()
                .ifPresent(stepExecution -> {
                    long writeCount = stepExecution.getWriteCount();
                    log.info("{} lines have been written.", writeCount);
                });
    }
}