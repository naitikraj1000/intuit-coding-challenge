# Producer-Consumer Pattern with Thread Synchronization

This project demonstrates the classic producer-consumer problem using Java's wait/notify mechanism for thread synchronization. The implementation shows how multiple threads can safely share a bounded queue using synchronized blocks, wait(), and notifyAll().

## Project Structure

```
src/main/java/com/example/producerconsumer/
├── queue/
│   └── BlockingQueue.java       # Custom blocking queue with wait/notify
├── producer/
│   └── Producer.java            # Producer thread implementation
├── consumer/
│   └── Consumer.java            # Consumer thread implementation
└── App.java                     # Main entry point
```

## Key Concepts Demonstrated

- **Thread Synchronization**: Using synchronized blocks to ensure mutual exclusion
- **Wait/Notify Mechanism**: Threads block when queue is full/empty and wake up when conditions change
- **Blocking Queue**: Custom implementation showing how producer blocks on full queue and consumer blocks on empty queue
- **Concurrent Programming**: Multiple threads safely accessing shared resources

## Prerequisites

- JDK 17 or higher
- Maven 3.6+

### Installing Maven (if not already installed)

**On Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install maven
```

**On macOS:**
```bash
brew install maven
```

**On Windows:**
1. Download Maven from https://maven.apache.org/download.cgi
2. Extract the archive to `C:\Program Files\Apache\maven`
3. Add `C:\Program Files\Apache\maven\bin` to your PATH environment variable

**Verify installation:**
```bash
mvn --version
```

## How to Run

```bash
cd producerConsumer
mvn clean compile
mvn exec:java
```

## How It Works

1. **Producer Thread**: Runs faster (100ms delay), attempts to add items to queue
2. **Consumer Thread**: Runs slower (400ms delay), attempts to remove items from queue
3. **Blocking Behavior**: 
   - When queue reaches capacity (5 items), producer calls `wait()` and blocks
   - When queue is empty, consumer calls `wait()` and blocks
   - `notifyAll()` wakes up waiting threads when space becomes available or items are added
4. **Synchronization**: All queue operations are wrapped in `synchronized` blocks to prevent race conditions

## Sample Output

```
=== Producer-Consumer Pattern with Thread Synchronization ===
Buffer Size: 5
Demonstrating: Wait/Notify mechanism, Blocking Queue, Thread Synchronization

Source Container: 30 items
Starting threads...

[CONSUMER] Attempting to consume...
[PRODUCER] Attempting to produce item: 1
[CONSUMER] BLOCKED - Queue is EMPTY. Waiting...
[PRODUCER] Produced item: 1 | Queue size: 1/5
[CONSUMER] Consumed item: 1 | Queue size: 0/5
[PRODUCER] Attempting to produce item: 2
[PRODUCER] Produced item: 2 | Queue size: 1/5
[PRODUCER] Attempting to produce item: 3
[PRODUCER] Produced item: 3 | Queue size: 2/5
[PRODUCER] Attempting to produce item: 4
[PRODUCER] Produced item: 4 | Queue size: 3/5
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 2 | Queue size: 2/5
[PRODUCER] Attempting to produce item: 5
[PRODUCER] Produced item: 5 | Queue size: 3/5
[PRODUCER] Attempting to produce item: 6
[PRODUCER] Produced item: 6 | Queue size: 4/5
[PRODUCER] Attempting to produce item: 7
[PRODUCER] Produced item: 7 | Queue size: 5/5
[PRODUCER] Attempting to produce item: 8
[PRODUCER] BLOCKED - Queue is FULL. Waiting...
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 3 | Queue size: 4/5
[PRODUCER] Produced item: 8 | Queue size: 5/5
[PRODUCER] Attempting to produce item: 9
[PRODUCER] BLOCKED - Queue is FULL. Waiting...
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 4 | Queue size: 4/5
[PRODUCER] Produced item: 9 | Queue size: 5/5
[PRODUCER] Attempting to produce item: 10
[PRODUCER] BLOCKED - Queue is FULL. Waiting...
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 5 | Queue size: 4/5
[PRODUCER] Produced item: 10 | Queue size: 5/5
[PRODUCER] Attempting to produce item: 11
[PRODUCER] BLOCKED - Queue is FULL. Waiting...
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 6 | Queue size: 4/5
[PRODUCER] Produced item: 11 | Queue size: 5/5
[PRODUCER] Attempting to produce item: 12
[PRODUCER] BLOCKED - Queue is FULL. Waiting...
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 7 | Queue size: 4/5
[PRODUCER] Produced item: 12 | Queue size: 5/5
...
[PRODUCER] Attempting to produce item: 30
[PRODUCER] BLOCKED - Queue is FULL. Waiting...
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 25 | Queue size: 4/5
[PRODUCER] Produced item: 30 | Queue size: 5/5
[PRODUCER] All items from source container transferred to queue.
[CONSUMER] Attempting to consume...
[CONSUMER] Consumed item: 26 | Queue size: 4/5

=== Stopping simulation ===

=== Final Statistics ===
Source Container: 30 items (original)
Total Produced: 30
Total Consumed: 26
Items in Queue: 4
Destination Container: 26 items
Destination Contents: [1, 2, e 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26]

Thread synchronization demonstrated using wait/notify mechanism.
```

## Unit Tests

The project includes comprehensive unit tests to verify the correctness of thread synchronization and blocking behavior. Run tests with:

```bash
mvn test
```

### Test Suite Overview

#### 1. Producer Blocks When Queue is Full
**What it tests:** Verifies that the producer thread correctly blocks when attempting to add items to a full queue.

**How it works:**
- Creates a BlockingQueue with capacity 2
- Fills the queue completely (adds 2 items)
- Starts a producer thread that attempts to add a 3rd item
- Uses `CountDownLatch` to ensure producer reaches the blocking state
- Verifies queue size remains 2 (producer is blocked)
- Consumer removes 1 item to unblock producer
- Verifies producer successfully adds item after being unblocked

**Why it matters:** Demonstrates that `wait()` is called correctly when queue is full, preventing buffer overflow.

#### 2. Consumer Blocks When Queue is Empty
**What it tests:** Verifies that the consumer thread correctly blocks when attempting to take items from an empty queue.

**How it works:**
- Creates an empty BlockingQueue with capacity 5
- Starts a consumer thread that attempts to take an item
- Uses `CountDownLatch` to ensure consumer reaches the blocking state
- Verifies queue size remains 0 (consumer is blocked)
- Producer adds 1 item to unblock consumer
- Verifies consumer successfully takes item after being unblocked

**Why it matters:** Demonstrates that `wait()` is called correctly when queue is empty, preventing underflow errors.

#### 3. Mutual Exclusion With Concurrent Access
**What it tests:** Verifies that multiple threads can safely access the shared queue without race conditions.

**How it works:**
- Creates a BlockingQueue with capacity 10
- Producer thread adds 20 items sequentially
- Consumer thread removes 20 items sequentially
- Both threads run concurrently
- Verifies final queue size is 0 (all items produced and consumed)

**Why it matters:** Demonstrates that `synchronized` blocks prevent race conditions and ensure thread safety. Without proper synchronization, the queue could become corrupted.

#### 4. Source to Destination Transfer (Integration Test)
**What it tests:** End-to-end validation of the complete producer-consumer pattern.

**How it works:**
- Creates source container with 10 items [1, 2, 3, ..., 10]
- Creates empty destination container
- Creates BlockingQueue with capacity 3 (small buffer to force blocking)
- Producer reads from source and puts items in queue
- Consumer takes from queue and stores in destination
- Verifies all 10 items are produced
- Verifies items arrive in correct sequential order
- Verifies at least 5 items are consumed

**Why it matters:** Validates the entire data flow: Source → Queue → Destination, ensuring no data loss or corruption.

### Test Results

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.example.producerconsumer.BlockingQueueTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.292 s

Running com.example.producerconsumer.ProducerConsumerIntegrationTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.532 s

Results:
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
Total time: 3.434 s
```

All tests pass successfully, confirming correct implementation of thread synchronization and blocking behavior.


