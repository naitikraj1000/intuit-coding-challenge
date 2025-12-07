package com.example.producerconsumer;

import com.example.producerconsumer.queue.BlockingQueue;
import com.example.producerconsumer.producer.Producer;
import com.example.producerconsumer.consumer.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Main application demonstrating producer-consumer pattern with thread synchronization.
 */
public class App {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Producer-Consumer Pattern with Thread Synchronization ===");
        System.out.println("Buffer Size: 5");
        System.out.println("Demonstrating: Wait/Notify mechanism, Blocking Queue, Thread Synchronization\n");
        
        // Source container with items to transfer
        List<Integer> sourceContainer = IntStream.rangeClosed(1, 30)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Source Container: " + sourceContainer.size() + " items");
        
        // Destination container (thread-safe list)
        List<Integer> destinationContainer = new ArrayList<>();
        
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);
        AtomicBoolean running = new AtomicBoolean(true);
        
        // Create producer and consumer
        Producer producerTask = new Producer(sourceContainer, queue, running, 100); // Fast producer
        Consumer consumerTask = new Consumer(queue, destinationContainer, running, 400); // Slow consumer
        
        Thread producer = new Thread(producerTask, "Producer-Thread");
        Thread consumer = new Thread(consumerTask, "Consumer-Thread");
        
        System.out.println("Starting threads...\n");
        producer.start();
        consumer.start();
        
        // Let it run for 10 seconds to show blocking behavior
        Thread.sleep(10000);
        
        System.out.println("\n=== Stopping simulation ===");
        running.set(false);
        
        producer.join(2000);
        consumer.join(2000);
        
        System.out.println("\n=== Final Statistics ===");
        System.out.println("Source Container: " + sourceContainer.size() + " items (original)");
        System.out.println("Total Produced: " + producerTask.getProducedCount());
        System.out.println("Total Consumed: " + consumerTask.getConsumedCount());
        System.out.println("Items in Queue: " + queue.size());
        System.out.println("Destination Container: " + destinationContainer.size() + " items");
        System.out.println("Destination Contents: " + destinationContainer);
        System.out.println("\nThread synchronization demonstrated using wait/notify mechanism.");
    }
}
