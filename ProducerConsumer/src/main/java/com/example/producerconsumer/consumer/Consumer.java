package com.example.producerconsumer.consumer;

import com.example.producerconsumer.queue.BlockingQueue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

// Consumer thread - takes items from queue and stores in destination
public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final List<Integer> destinationContainer;
    private final AtomicBoolean running;
    private final int delayMs;
    private final Random random = new Random();
    private int consumedCount = 0;

    public Consumer(BlockingQueue<Integer> queue, List<Integer> destinationContainer, AtomicBoolean running, int delayMs) {
        this.queue = queue;
        this.destinationContainer = destinationContainer;
        this.running = running;
        this.delayMs = delayMs;
    }

    @Override
    public void run() {
        try {
            while (running.get()) {

                System.out.println("[CONSUMER] Attempting to consume...");

                // Check if queue is empty - will block here if empty
                synchronized (queue) {
                    if (queue.size() == 0) {
                        System.out.println("[CONSUMER] BLOCKED - Queue is EMPTY. Waiting...");
                    }
                }
                
                
                Integer item = queue.take(); // This blocks if queue is empty (uses wait())
                destinationContainer.add(item); // Store in destination container
                System.out.println("[CONSUMER] Consumed item: " + item + " | Queue size: " + queue.size() + "/" + queue.capacity);
                consumedCount++;
                
                // Add randomness: delayMs ± 100ms
                int actualDelay = delayMs + random.nextInt(201) - 100;
                Thread.sleep(Math.max(10, actualDelay));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getConsumedCount() {
        return consumedCount;
    }
}
