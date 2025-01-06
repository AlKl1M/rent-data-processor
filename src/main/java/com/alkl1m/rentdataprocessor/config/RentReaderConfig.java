package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import com.alkl1m.rentdataprocessor.reader.ThreadSafeMultiResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * Конфигурация для чтения данных аренды с использованием потокобезопасного многоресурсного чтения.
 * Класс создаёт бины для конфигурации чтения данных аренды из нескольких ресурсов с помощью
 * ThreadSafeMultiResourceReader, MultiResourceItemReader и JsonItemReader.
 *
 * @author AlKl1M
 */
@Slf4j
@Configuration
public class RentReaderConfig {

    @Value("${input.folder.rent}")
    private Resource[] rentInputResources;

    /**
     * Метод создаёт и настраивает потокобезопасный многоресурсный читатель для данных аренды.
     * Устанавливает ресурсы для чтения и возвращает настроенный объект ThreadSafeMultiResourceReader.
     *
     * @return настроенный ThreadSafeMultiResourceReader для данных аренды
     */
    @Bean
    public ThreadSafeMultiResourceReader<RentDto> rentResourceReader() {
        log.info("Configuring ThreadSafeMultiResourceReader for rent resources");
        var multiResourceReader = new ThreadSafeMultiResourceReader<>(multiResourceItemReader());
        multiResourceReader.setResources(rentInputResources);
        return multiResourceReader;
    }

    /**
     * Метод создаёт и настраивает MultiResourceItemReader для данных аренды.
     * Настроенный читатель будет использовать JsonItemReader для обработки данных в формате JSON.
     *
     * @return настроенный MultiResourceItemReader для данных аренды
     */
    @Bean
    public MultiResourceItemReader<RentDto> multiResourceItemReader() {
        log.info("Creating MultiResourceItemReader for rent resources");
        return new MultiResourceItemReaderBuilder<RentDto>()
                .name("rentResourceItemReader")
                .resources(rentInputResources)
                .delegate(jsonRentItemReader())
                .build();
    }

    /**
     * Метод создаёт и настраивает JsonItemReader для данных аренды.
     * Используется JacksonJsonObjectReader для чтения объектов RentDto в формате JSON.
     *
     * @return настроенный JsonItemReader для данных аренды
     */
    @Bean
    public ResourceAwareItemReaderItemStream<RentDto> jsonRentItemReader() {
        log.info("Creating JsonItemReader for rent data");
        return new JsonItemReaderBuilder<RentDto>()
                .name("jsonRentItemReader")
                .jsonObjectReader(new JacksonJsonObjectReader<>(RentDto.class))
                .strict(false)
                .build();
    }

}
