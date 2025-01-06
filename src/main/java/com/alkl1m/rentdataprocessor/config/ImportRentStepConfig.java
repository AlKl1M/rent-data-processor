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

/**
 * Конфигурация шага импорта данных аренды.
 *
 * @author AlKl1M
 */
@Slf4j
@Configuration
public class ImportRentStepConfig {

    @Value("${input.chunk.size:100}")
    private int chunkSize;

    /**
     * Метод создаёт и настраивает шаг для импорта данных аренды.
     * Шаг конфигурируется с заданным размером чанка, читателем, процессором и писателем для обработки данных аренды.
     * Для выполнения используется виртуальный поток с помощью VirtualThreadTaskExecutor.
     *
     * @param jobRepository              репозиторий для хранения данных о заданиях
     * @param platformTransactionManager менеджер транзакций
     * @param rentResourceReader         компонент для чтения данных аренды
     * @param rentProcessor              компонент для обработки данных аренды
     * @param rentWriter                 компонент для записи данных аренды
     * @return настроенный шаг импорта данных аренды
     */
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

    /**
     * Метод создаёт и настраивает VirtualThreadTaskExecutor для выполнения шагов импорта с виртуальными потоками.
     * Задаётся префикс для потоков.
     *
     * @return настроенный VirtualThreadTaskExecutor
     */
    @Bean
    public VirtualThreadTaskExecutor virtualThreadTaskExecutor() {
        log.info("Creating VirtualThreadTaskExecutor with prefix: Rent-Thread-Executor-");
        return new VirtualThreadTaskExecutor("Rent-Thread-Executor-");
    }

}
