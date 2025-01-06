package com.alkl1m.rentdataprocessor.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThreadSafeMultiResourceReaderTest {

    @Mock
    private MultiResourceItemReader<String> mockDelegateReader;

    @InjectMocks
    private ThreadSafeMultiResourceReader<String> threadSafeReader;

    @Test
    void testRead_ValidData_ReturnsCorrectData() throws Exception {
        when(mockDelegateReader.read()).thenReturn("data");

        String result = threadSafeReader.read();

        verify(mockDelegateReader, times(1)).read();
        assertEquals("data", result);
    }

    @Test
    void testSetResources_ValidResources_DelegatesCorrectly() {
        Resource[] resources = new Resource[0];

        threadSafeReader.setResources(resources);

        verify(mockDelegateReader, times(1)).setResources(resources);
    }

    @Test
    void testOpen_ValidExecutionContext_DelegatesOpen() {
        ExecutionContext executionContext = new ExecutionContext();

        threadSafeReader.open(executionContext);

        verify(mockDelegateReader, times(1)).open(executionContext);
    }

    @Test
    void testClose_DelegatesClose() {
        threadSafeReader.close();

        verify(mockDelegateReader, times(1)).close();
    }
}