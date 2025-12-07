package com.example.producerconsumer;

import com.example.producerconsumer.consumer.Consumer;
import com.example.producerconsumer.producer.Producer;
import com.example.producerconsumer.queue.BlockingQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ProducerConsumerIntegrationTest {

    @Test
    void testSourceToDestinationTransfer() throws InterruptedException {
        // Setup source with 10 items
        List<Integer> sourceContainer = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(Collectors.toList());
        
        List<Integer> destinationContainer = new ArrayList<>();
        BlockingQueue<Integer> queue = new BlockingQueue<>(3);
        AtomicBoolean running = new AtomicBoolean(true);
        
        Producer producer = new Producer(sourceContainer, queue, running, 10);
        Consumer consumer = new Consumer(queue, destinationContainer, running, 10);
        
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        
        producerThread.start();
        consumerThread.start();
        producerThread.join(2000);
        
        Thread.sleep(200);
        running.set(false);
        consumerThread.join(1000);
        
        // Check results
        assertEquals(10, producer.getProducedCount());
        assertTrue(consumer.getConsumedCount() >= 5);
        
        for (int i = 0; i < destinationContainer.size(); i++) {
            assertEquals(i + 1, destinationContainer.get(i),
                    "Items should be transferred in sequential order");
        }
    }
}
