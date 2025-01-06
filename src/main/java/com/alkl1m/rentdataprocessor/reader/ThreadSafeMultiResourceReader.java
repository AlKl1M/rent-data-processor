package com.alkl1m.rentdataprocessor.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeMultiResourceReader<T> implements ItemReader<T> {

    private final MultiResourceItemReader<T> delegateReader;
    private final Lock reentrantLock = new ReentrantLock();

    public ThreadSafeMultiResourceReader(MultiResourceItemReader<T> delegateReader) {
        this.delegateReader = delegateReader;
    }

    @Override
    public T read() throws Exception {
        reentrantLock.lock();
        try {
            return delegateReader.read();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void setResources(Resource[] resources) {
        reentrantLock.lock();
        try {
            delegateReader.setResources(resources);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void open(ExecutionContext executionContext) {
        reentrantLock.lock();
        try {
            delegateReader.open(executionContext);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void close() {
        reentrantLock.lock();
        try {
            delegateReader.close();
        } finally {
            reentrantLock.unlock();
        }
    }
}
