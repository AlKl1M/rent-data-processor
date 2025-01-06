package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для записи данных аренды.
 *
 * @author AlKl1M
 */
@Slf4j
@Configuration
public class RentWriterConfig {

    /**
     * Метод создаёт и возвращает ItemWriter для записи данных аренды.
     * Логирует данные аренды при их записи.
     *
     * @return ItemWriter для записи объектов RentDto
     */
    @Bean
    public ItemWriter<RentDto> rentWriter() {
        return rents -> log.info("Writing rent data: {}", rents);
    }

}
