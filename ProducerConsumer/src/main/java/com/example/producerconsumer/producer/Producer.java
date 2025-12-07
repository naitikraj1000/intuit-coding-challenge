package com.example.producerconsumer.producer;

import com.example.producerconsumer.queue.BlockingQueue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

// Producer thread - takes items from source and puts them in queue
public class Producer implements Runnable {
    private final List<Integer> sourceContainer;
    private final BlockingQueue<Integer> queue;
    private final AtomicBoolean running;
    private final int delayMs;
    private final Random random = new Random();
    private int producedCount = 0;

    public Producer(List<Integer> sourceContainer, BlockingQueue<Integer> queue, AtomicBoolean running, int delayMs) {
        this.sourceContainer = sourceContainer;
        this.queue = queue;
        this.running = running;
        this.delayMs = delayMs;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < sourceContainer.size() && running.get(); i++) {
                Integer item = sourceContainer.get(i);
                System.out.println("[PRODUCER] Attempting to produce item: " + item);
                
                // Check if queue is full - will block here if full
                synchronized (queue) {
                    if (queue.size() >= queue.capacity) {
                        System.out.println("[PRODUCER] BLOCKED - Queue is FULL. Waiting...");
                    }
                }
                
                queue.put(item); // This blocks if queue is full (uses wait())
                System.out.println("[PRODUCER] Produced item: " + item + " | Queue size: " + queue.size() + "/" + queue.capacity);
                producedCount++;
                
                // Add randomness: delayMs ± 50ms
                int actualDelay = delayMs + random.nextInt(101) - 50;
                Thread.sleep(Math.max(10, actualDelay));
            }
            System.out.println("[PRODUCER] All items from source container transferred to queue.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getProducedCount() {
        return producedCount;
    }
}
