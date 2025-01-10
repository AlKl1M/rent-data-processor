package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import com.alkl1m.rentdataprocessor.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для обработки данных аренды.
 *
 * @author AlKl1M
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RentProcessorConfig {

    private final RentService rentService;

    /**
     * Метод создаёт и настраивает ItemProcessor для обработки данных аренды.
     * Логирует данные аренды перед их обработкой и сохраняет или обновляет их с помощью RentService.
     *
     * @return настроенный ItemProcessor для данных аренды
     */
    @Bean
    public ItemProcessor<RentDto, RentDto> rentProcessor() {
        return rentDto -> {
            log.info("Processing rent data: {}", rentDto);
            rentService.saveOrUpdateRent(rentDto);
            return rentDto;
        };
    }

}
