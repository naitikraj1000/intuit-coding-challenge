package com.example.producerconsumer;

import com.example.producerconsumer.queue.BlockingQueue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BlockingQueue to verify thread-safe blocking behavior and mutual exclusion.
 */
class BlockingQueueTest {

    @Test
    @DisplayName("Producer blocks when queue is full")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testProducerBlocksWhenQueueFull() throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>(2);
        CountDownLatch producerReady = new CountDownLatch(1);
        
        // Fill the queue to capacity
        queue.put(1);
        queue.put(2);
        assertEquals(2, queue.size());
        
        // Start producer thread that will block
        Thread producer = new Thread(() -> {
            try {
                producerReady.countDown();
                queue.put(3); // This should block until consumer takes an item
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        
        // Wait for producer to reach blocking state
        producerReady.await();
        Thread.sleep(100);
        
        // Queue should still be full (producer is blocked)
        assertEquals(2, queue.size());
        
        // Consumer takes an item, unblocking producer
        queue.take();
        
        // Give producer time to add item
        producer.join(500);
        
        // Verify producer was able to add item after being unblocked
        assertEquals(2, queue.size());
    }

    @Test
    @DisplayName("Consumer blocks when queue is empty")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testConsumerBlocksWhenQueueEmpty() throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);
        CountDownLatch consumerReady = new CountDownLatch(1);
        
        // Start consumer thread that will block on empty queue
        Thread consumer = new Thread(() -> {
            try {
                consumerReady.countDown();
                queue.take(); // This should block until producer adds an item
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        
        // Wait for consumer to reach blocking state
        consumerReady.await();
        Thread.sleep(100);
        
        // Queue should still be empty (consumer is blocked)
        assertEquals(0, queue.size());
        
        // Producer adds an item, unblocking consumer
        queue.put(1);
        
        // Give consumer time to take item
        consumer.join(500);
        
        // Verify consumer was able to take item after being unblocked
        assertEquals(0, queue.size());
    }
    @Test
    @DisplayName("Mutual exclusion with concurrent access")
    void testMutualExclusionWithConcurrentAccess() throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>(10);
        int itemCount = 20;
        
        // Start producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= itemCount; i++) {
                    queue.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Start consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= itemCount; i++) {
                    queue.take();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        producer.start();
        consumer.start();
        
        producer.join(3000);
        consumer.join(3000);
        
        // All items should be produced and consumed (no race conditions)
        assertEquals(0, queue.size());
    }
}
