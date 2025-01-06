package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import com.alkl1m.rentdataprocessor.entity.Rent;
import com.alkl1m.rentdataprocessor.listener.CustomJobExecutionListener;
import com.alkl1m.rentdataprocessor.reader.MultiResourceReaderThreadSafe;
import com.alkl1m.rentdataprocessor.repository.RentRepository;
import com.alkl1m.rentdataprocessor.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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

@Configuration
public class ImportRentJobConfig {

    @Value("${input.folder.rent}")
    private Resource[] resources;

    private final CustomJobExecutionListener customJobExecutionListener;
    private final RentService rentService;

    public ImportRentJobConfig(CustomJobExecutionListener customJobExecutionListener, RentService rentService) {
        this.customJobExecutionListener = customJobExecutionListener;
        this.rentService = rentService;
    }

    @Bean
    public Job importRentJob(JobRepository jobRepository, Step importRentStep) {
        return new JobBuilder("importRentJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importRentStep)
                .listener(customJobExecutionListener)
                .build();
    }

    @Bean
    public Step importRentStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("importRentStep", jobRepository)
                .<RentDto, RentDto>chunk(100, platformTransactionManager)
                .reader(multiResourceReaderThreadSafe())
                .processor(this::rentProcessor)
                .writer(rents -> System.out.println(rents))
                .taskExecutor(virtualThreadTaskExecutor())
                .build();
    }

    @Bean
    public VirtualThreadTaskExecutor virtualThreadTaskExecutor() {
        return new VirtualThreadTaskExecutor("Rent-Thread-Executor-");
    }

    public MultiResourceReaderThreadSafe<RentDto> multiResourceReaderThreadSafe() {
        var multiResourceReader = new MultiResourceReaderThreadSafe<>(multiResourceItemReader());
        multiResourceReader.setResources(resources);
        return multiResourceReader;
    }

    public MultiResourceItemReader<RentDto> multiResourceItemReader() {
        return new MultiResourceItemReaderBuilder<RentDto>()
                .name("rent resources reader")
                .resources(resources)
                .delegate(rentItemReader())
                .build();
    }

    public ResourceAwareItemReaderItemStream<RentDto> rentItemReader() {
        return new JsonItemReaderBuilder<RentDto>()
                .name("rent reader")
                .jsonObjectReader(new JacksonJsonObjectReader<>(RentDto.class))
                .strict(false)
                .build();
    }

    private RentDto rentProcessor(RentDto rentDto) {
        rentService.saveOrUpdateRent(rentDto);
        return rentDto;
    }

}
