package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RentWriterConfig {

    @Bean
    public ItemWriter<RentDto> rentWriter() {
        return rents -> log.info("Writing rent data: {}", rents);
    }

}
