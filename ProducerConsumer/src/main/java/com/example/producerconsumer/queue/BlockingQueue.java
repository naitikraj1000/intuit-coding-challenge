package com.example.producerconsumer.queue;

import java.util.ArrayDeque;
import java.util.Queue;

// Custom blocking queue using wait/notify
public class BlockingQueue<T> {
    public final int capacity;
    private final Queue<T> queue;

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
        this.queue = new ArrayDeque<>(capacity);
    }

    // Add item to queue (blocks if full)
    public void put(T item) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() >= capacity) {
                queue.wait();
            }
            queue.add(item);
            queue.notifyAll();
        }
    }

    // Remove item from queue (blocks if empty)
    public T take() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            T item = queue.remove();
            queue.notifyAll();
            return item;
        }
    }

    public int size() {
        synchronized (queue) {
            return queue.size();
        }
    }
}
