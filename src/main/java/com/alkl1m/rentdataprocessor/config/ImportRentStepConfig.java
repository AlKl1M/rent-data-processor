package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class ImportRentStepConfig {

    @Value("${input.chunk.size:100}")
    private int chunkSize;

    @Bean
    public Step importRentDataStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                                   ItemReader<RentDto> rentResourceReader,
                                   ItemProcessor<RentDto, RentDto> rentProcessor,
                                   ItemWriter<RentDto> rentWriter) {
        log.info("Configuring step: importRentDataStep with chunk size: {}", chunkSize);
        return new StepBuilder("importRentDataStep", jobRepository)
                .<RentDto, RentDto>chunk(chunkSize, platformTransactionManager)
                .reader(rentResourceReader)
                .processor(rentProcessor)
                .writer(rentWriter)
                .taskExecutor(virtualThreadTaskExecutor())
                .build();
    }

    @Bean
    public VirtualThreadTaskExecutor virtualThreadTaskExecutor() {
        log.info("Creating VirtualThreadTaskExecutor with prefix: Rent-Thread-Executor-");
        return new VirtualThreadTaskExecutor("Rent-Thread-Executor-");
    }

}
