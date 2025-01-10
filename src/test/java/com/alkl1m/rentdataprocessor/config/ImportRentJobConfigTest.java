package com.alkl1m.rentdataprocessor.config;

import com.alkl1m.rentdataprocessor.entity.Rent;
import com.alkl1m.rentdataprocessor.repository.RentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@DisplayName("Тесты для джоб связанных с данными аренды.")
class ImportRentJobConfigTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
                    .withDatabaseName("rent")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private RentRepository rentRepository;

    @Test
    @DisplayName("Тест, проверяющий, что с момента начала запуска сервис прогонит джобу и сохранит 1000 данных об аренде из файла.")
    void givenNoRentRecords_whenCronJobExecuted_then1000RentRecordsShouldBeCreated() {
        List<Rent> beforeCron = rentRepository.findAll();
        assertEquals(0, beforeCron.size());

        Awaitility.await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    List<Rent> afterCron = rentRepository.findAll();
                    assert afterCron.size() == 1000;
                });
    }

}
