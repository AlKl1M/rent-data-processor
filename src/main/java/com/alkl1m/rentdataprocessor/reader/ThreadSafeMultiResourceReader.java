package com.alkl1m.rentdataprocessor.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Потокобезопасный класс ThreadSafeMultiResourceReader, реализующий интерфейс ItemReader.
 * Класс используется для чтения данных с несколькими ресурсами. Все операции чтения
 * защищены с помощью блокировки для обеспечения потокобезопасности.
 * Данный класс делегирует операции чтения и управления ресурсами объекту MultiResourceItemReader
 * и синхронизирует доступ с помощью ReentrantLock.
 *
 * @author AlKl1M
 */
@RequiredArgsConstructor
public class ThreadSafeMultiResourceReader<T> implements ItemReader<T> {

    private final MultiResourceItemReader<T> delegateReader;
    private final Lock reentrantLock = new ReentrantLock();

    /**
     * Метод для чтения данных с блокировкой.
     * Обеспечивает потокобезопасность при чтении данных.
     *
     * @return объект типа T, считанный из ресурса
     * @throws Exception если происходит ошибка при чтении
     */
    @Override
    public T read() throws Exception {
        reentrantLock.lock();
        try {
            return delegateReader.read();
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * Метод для установки ресурсов, с которыми будет работать читатель.
     * Обеспечивает потокобезопасность при изменении ресурсов.
     *
     * @param resources массив ресурсов для установки
     */
    public void setResources(Resource[] resources) {
        reentrantLock.lock();
        try {
            delegateReader.setResources(resources);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * Метод для открытия чтения данных с блокировкой.
     * Обеспечивает потокобезопасность при инициализации чтения.
     *
     * @param executionContext контекст выполнения
     */
    public void open(ExecutionContext executionContext) {
        reentrantLock.lock();
        try {
            delegateReader.open(executionContext);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * Метод для закрытия чтения данных с блокировкой.
     * Обеспечивает потокобезопасность при завершении чтения.
     */
    public void close() {
        reentrantLock.lock();
        try {
            delegateReader.close();
        } finally {
            reentrantLock.unlock();
        }
    }

}
