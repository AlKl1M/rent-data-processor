package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import com.alkl1m.rentdataprocessor.service.RentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RentProcessorConfig {

    private final RentService rentService;

    public RentProcessorConfig(RentService rentService) {
        this.rentService = rentService;
    }

    @Bean
    public ItemProcessor<RentDto, RentDto> rentProcessor() {
        return rentDto -> {
            log.info("Processing rent data: {}", rentDto);
            rentService.saveOrUpdateRent(rentDto);
            return rentDto;
        };
    }
}