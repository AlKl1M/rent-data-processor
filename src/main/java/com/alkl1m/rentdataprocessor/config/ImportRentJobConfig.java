package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import com.alkl1m.rentdataprocessor.entity.Rent;
import com.alkl1m.rentdataprocessor.listener.CustomJobExecutionListener;
import com.alkl1m.rentdataprocessor.reader.ThreadSafeMultiResourceReader;
import com.alkl1m.rentdataprocessor.repository.RentRepository;
import com.alkl1m.rentdataprocessor.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

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