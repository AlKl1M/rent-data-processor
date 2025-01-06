package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.listener.CustomJobExecutionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ImportRentJobConfig {

    private final CustomJobExecutionListener customJobExecutionListener;

    public ImportRentJobConfig(CustomJobExecutionListener customJobExecutionListener) {
        this.customJobExecutionListener = customJobExecutionListener;
    }

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
