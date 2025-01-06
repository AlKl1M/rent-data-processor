package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.listener.CustomJobExecutionListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация задания импорта данных аренды.
 *
 * @author AlKl1M
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ImportRentJobConfig {

    private final CustomJobExecutionListener customJobExecutionListener;

    /**
     * Метод создаёт и настраивает задание импорта данных аренды.
     * Задание состоит из шага импорта данных и слушателя выполнения задания.
     * Используется инкрементатор для увеличения идентификатора запуска.
     *
     * @param jobRepository      репозиторий для хранения данных о заданиях
     * @param importRentDataStep шаг для импорта данных аренды
     * @return настроенное задание импорта данных аренды
     */
    @Bean
    public Job importRentJob(JobRepository jobRepository, Step importRentDataStep) {
        log.info("Creating job: importRentJob");
        return new JobBuilder("importRentJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importRentDataStep)
                .listener(customJobExecutionListener)
                .build();
    }

}
