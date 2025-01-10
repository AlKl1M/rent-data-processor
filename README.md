# Rent Data Processor

Выполняет параллельную обработку данных, используя виртуальные потоки. Под обработкой данных подразумевается парсинг
документов формата json в сущности и сохранение их в БД. Обработка данных выполняется с определенным периодом для
учебных целей.

## Стек технологий

- **Java 21**
- **Spring Framework**
    - **Spring Boot** (v. 3.4.1)
    - **Spring Batch**
    - **Spring Data JPA**
- **PostgreSQL**
- **Jackson**
- **Lombok**
- **Инструменты для тестирования**
    - **Spring Boot Test**
    - **Spring Boot Testcontainers**
    - **Awaitility** (v. 4.2.0)
    - **Testcontainers** (Junit Jupiter)
