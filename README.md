# Intuit Coding Challenge

This repository contains two Java projects demonstrating advanced programming concepts and best practices:

## 📥 Clone This Repository

```bash
git clone https://github.com/naitikraj1000/intuit-coding-challenge.git
cd intuit-coding-challenge
```

## 📁 Projects Overview

### 1. ProducerConsumer - Thread Synchronization Pattern
A classic producer-consumer pattern implementation demonstrating thread synchronization using wait/notify mechanisms.

**Key Features:**
- Custom blocking queue implementation
- Thread synchronization with synchronized blocks
- Wait/notify mechanism for inter-thread communication
- Multiple producer/consumer support
- Comprehensive integration tests

**Technologies:** Java 17, Maven, JUnit 5, Concurrent Programming

[📖 View ProducerConsumer Documentation →](./ProducerConsumer/README.md)

### 2. DataAnalysis - Sales Data Analysis with Java Streams
A comprehensive sales data analysis application showcasing functional programming using Java Streams API.

**Key Features:**
- 13 different analysis methods using Java Streams
- CSV data processing with functional programming
- Advanced Stream operations (map, filter, collect, groupingBy)
- Comprehensive test coverage
- Real-world sales data analysis

**Technologies:** Java 17, Maven, JUnit 5, Java Streams API

[📖 View DataAnalysis Documentation →](./DataAnalysis/README.md)

## 🚀 Quick Start

### Prerequisites

#### Install JDK 17 or Higher
**On Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**On macOS:**
```bash
brew install openjdk@17
```

**On Windows:**
Download from [Oracle JDK Downloads](https://www.oracle.com/java/technologies/downloads/)

#### Install Maven 3.6+
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
2. Extract to `C:\Program Files\Apache\maven`
3. Add `C:\Program Files\Apache\maven\bin` to PATH

#### Verify Installation
```bash
java -version
mvn --version
```

## 🏃 Running the Projects

### 1️⃣ ProducerConsumer Project

Demonstrates thread synchronization using wait/notify mechanism with a custom blocking queue. Shows how producers and consumers interact with proper thread coordination.

```bash
cd ProducerConsumer
mvn clean compile exec:java
mvn test
```

For detailed output examples, synchronization behavior, and complete test coverage, see the [ProducerConsumer README](./ProducerConsumer/README.md).

---

### 2️⃣ DataAnalysis Project

Analyzes sales data from a CSV file using Java Streams API. Performs 13 different analysis operations including revenue calculations, top customers, and data grouping.

```bash
cd DataAnalysis
mvn clean compile exec:java
mvn test
```

For detailed output examples, analysis methods, and complete test coverage, see the [DataAnalysis README](./DataAnalysis/README.md).

## 📊 Project Structure

```
intuit-coding-challenge/
├── ProducerConsumer/          # Thread synchronization project
│   ├── src/
│   │   ├── main/
│   │   │   └── java/         # Source code
│   │   └── test/             # Unit & integration tests
│   ├── pom.xml
│   └── README.md
│
├── DataAnalysis/              # Sales data analysis project
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Source code
│   │   │   └── resources/    # sales.csv data file
│   │   └── test/             # Unit tests
│   ├── pom.xml
│   └── README.md
│
└── README.md                  # This file
```

##  Objectives

### ProducerConsumer Project Demonstrates:
- Thread synchronization using synchronized blocks
- Wait/notify mechanism for thread communication
- Custom blocking queue implementation
- Concurrent programming best practices
- Producer-consumer pattern
## 🧪 Running All Tests

Run tests for both projects:
```bash
# From root directory
mvn -f ProducerConsumer/pom.xml test
mvn -f DataAnalysis/pom.xml test

# Or run from each project directory
cd ProducerConsumer && mvn test
cd ../DataAnalysis && mvn test
```

## 📝 Testing Coverage

### ProducerConsumer
- Unit tests for BlockingQueue operations
- Integration tests for producer-consumer interaction
- Thread safety verification
- Synchronization testing

### DataAnalysis
- 13 unit tests covering all analysis methods
- Edge cases and data validation
- Stream operations verification
### DataAnalysis
- 13 unit tests covering all analysis methods
- Edge cases and data validation
- Stream operations verification

### ProducerConsumer
- Unit tests for BlockingQueue operations
- Integration tests for producer-consumer interaction
- Thread safety verification
- Synchronization testing

## 🛠️ Build Commands

### Clean and Build Both Projects
```bash
# From root directory
mvn -f ProducerConsumer/pom.xml clean package
mvn -f DataAnalysis/pom.xml clean package
```

### Generate JAR Files
```bash
cd ProducerConsumer
mvn clean package
# Creates: target/producer-consumer-1.0-SNAPSHOT.jar

cd ../DataAnalysis
mvn clean package
# Creates: target/data-analysis-1.0-SNAPSHOT.jar
```

## 💡 Key Takeaways

1. **Thread Safety**: Proper synchronization is critical for concurrent applications
2. **Functional Programming**: Modern Java leverages streams for clean, declarative code
3. **Testing**: Comprehensive tests ensure reliability and correctness
4. **Code Organization**: Well-structured projects are easier to maintain and extend

## 👤 Author

Naitik Raj
---

**Note:** Both projects include comprehensive README files with detailed explanations, code examples, and expected outputs. Navigate to each project folder for more information.
