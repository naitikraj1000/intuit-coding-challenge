# Sales Data Analysis with Java Streams

This project demonstrates functional programming and data analysis using Java Streams API. It performs various aggregation and grouping operations on sales data from a CSV file.

## Project Structure

- `SalesRecord.java` - Data model representing a single sales transaction
- `SalesAnalysis.java` - Main analysis class with Stream-based operations
- `SalesAnalysisTest.java` - Comprehensive unit tests for all analysis methods
- `sales.csv` - Sample sales data

## Key Concepts Demonstrated

- **Functional Programming**: Using lambda expressions and method references
- **Stream Operations**: map, filter, collect, groupingBy, summingDouble, etc.
- **Data Aggregation**: Summing, averaging, counting, and grouping data
- **Lambda Expressions**: Concise functional-style programming
- **Collectors**: Advanced grouping and reduction operations

## Analysis Operations

**13 analysis methods implemented**, demonstrating various Stream operations:

| # | Method | Description | Stream Operations Used |
|---|--------|-------------|------------------------|
| 1 | `getTotalRevenue()` | Calculate total revenue | `mapToDouble`, `sum` |
| 2 | `getRevenueByProductLine()` | Group revenue by product line | `groupingBy`, `summingDouble` |
| 3 | `getRevenueByTerritory()` | Group revenue by territory | `groupingBy`, `summingDouble` |
| 4 | `getRevenueByDealSize()` | Group revenue by deal size | `groupingBy`, `summingDouble` |
| 5 | `getTopCustomers(n)` | Find top N customers by revenue | `groupingBy`, `sorted`, `limit` |
| 6 | `getTopCountries(n)` | Find top N countries by revenue | `groupingBy`, `sorted`, `limit` |
| 7 | `getAverageOrderValue()` | Calculate average order value | `mapToDouble`, `average` |
| 8 | `getTotalTransactions()` | Count total transactions | `size` |
| 9 | `getQuantityByProductLine()` | Sum quantities by product line | `groupingBy`, `summingInt` |
| 10 | `getRevenueByYear()` | Group revenue by year | `groupingBy`, `summingDouble` |
| 11 | `getHighValueOrders(threshold)` | Filter orders above threshold | `filter`, `sorted`, `collect` |
| 12 | `getDistinctCustomerCount()` | Count unique customers | `map`, `distinct`, `count` |
| 13 | `getOrderStatusDistribution()` | Count orders by status | `groupingBy`, `counting` |

**Note:** The console output displays 10 sections, with Section 1 combining 4 related methods (getTotalRevenue, getTotalTransactions, getAverageOrderValue, and getDistinctCustomerCount) for a clean summary view.

## Prerequisites

- JDK 17 or higher
- Maven 3.6+

## How to Run

```bash
cd DataAnalysis
mvn clean compile
mvn exec:java
```

## Run Tests

```bash
mvn test
```

## Sample Output

```
╔═══════════════════════════════════════════════════════════════════════════╗
║              Sales Data Analysis - Java Streams Demonstration             ║
╚═══════════════════════════════════════════════════════════════════════════╝

1. TOTAL REVENUE
   ──────────────────────────────────────────────────────────────────────
   Total Revenue: $10,032,628.85
   Total Transactions: 2,823
   Average Order Value: $3,553.89
   Distinct Customers: 92

2. REVENUE BY PRODUCT LINE
   ──────────────────────────────────────────────────────────────────────
   Classic Cars:                  $3,919,615.66
   Vintage Cars:                  $1,903,150.84
   Motorcycles:                   $1,166,388.34
   Trucks and Buses:              $1,127,789.84
   Planes:                        $  975,003.57
   Ships:                         $  714,437.13
   Trains:                        $  226,243.47

3. REVENUE BY TERRITORY
   ──────────────────────────────────────────────────────────────────────
   EMEA:                          $4,979,272.41
   NA:                            $3,852,061.39
   APAC:                          $  746,121.83
   Japan:                         $  455,173.22

4. REVENUE BY DEAL SIZE
   ──────────────────────────────────────────────────────────────────────
   Medium:                        $6,087,432.24
   Small:                         $2,643,077.35
   Large:                         $1,302,119.26

5. TOP 10 CUSTOMERS BY REVENUE
   ──────────────────────────────────────────────────────────────────────
   Euro Shopping Channel                              $912,294.11
   Mini Gifts Distributors Ltd.                       $654,858.06
   "Australian Collectors, Co."                       $200,995.41
   Muscle Machine Inc                                 $197,736.94
   La Rochelle Gifts                                  $180,124.90
   "Dragon Souveniers, Ltd."                          $172,989.68
   Land of Toys Inc.                                  $164,069.44
   The Sharp Gifts Warehouse                          $160,010.27
   "AV Stores, Co."                                   $157,807.81
   "Anna's Decorations, Ltd"                          $153,996.13

6. TOP 10 COUNTRIES BY REVENUE
   ──────────────────────────────────────────────────────────────────────
   USA:                           $3,627,982.83
   Spain:                         $1,215,686.92
   France:                        $1,110,916.52
   Australia:                     $  630,623.10
   UK:                            $  478,880.46
   Italy:                         $  374,674.31
   Finland:                       $  329,581.91
   Norway:                        $  307,463.70
   Singapore:                     $  288,488.41
   Denmark:                       $  245,637.15

7. REVENUE BY YEAR
   ──────────────────────────────────────────────────────────────────────
   2003: $3,516,979.54
   2004: $4,724,162.60
   2005: $1,791,486.71

8. QUANTITY SOLD BY PRODUCT LINE
   ──────────────────────────────────────────────────────────────────────
   Classic Cars:                    33,992 units
   Vintage Cars:                    21,069 units
   Motorcycles:                     11,663 units
   Trucks and Buses:                10,777 units
   Planes:                          10,727 units
   Ships:                            8,127 units
   Trains:                           2,712 units

9. ORDER STATUS DISTRIBUTION
   ──────────────────────────────────────────────────────────────────────
   Shipped:                          2,617 orders
   Cancelled:                           60 orders
   Resolved:                            47 orders
   On Hold:                             44 orders
   In Process:                          41 orders
   Disputed:                            14 orders

10. HIGH VALUE ORDERS (> $10,000) - Top 5
    ──────────────────────────────────────────────────────────────────────
    Order #10407    | Vintage Cars              | The Sharp Gifts Ware | $ 14,082.80
    Order #10322    | Vintage Cars              | Online Diecast Creat | $ 12,536.50
    Order #10424    | Classic Cars              | Euro Shopping Channe | $ 12,001.00
    Order #10412    | Classic Cars              | Euro Shopping Channe | $ 11,887.80
    Order #10403    | Motorcycles               | "UK Collectables, Lt | $ 11,886.60
    Total high-value orders: 16

╔═══════════════════════════════════════════════════════════════════════════╗
║                         Analysis Complete                                 ║
╚═══════════════════════════════════════════════════════════════════════════╝
```

## Testing Coverage

All 13 analysis methods have comprehensive **unit tests using mock data** for fast, isolated, and predictable testing.

### Testing Approach
- **Mock Data**: Tests use 10 controlled records with known values instead of the real CSV file
- **Dependency Injection**: `SalesAnalysis` accepts a `List<SalesRecord>` for testing
- **Fast Execution**: No file I/O, tests run in ~0.18 seconds
- **Precise Assertions**: Tests verify exact expected values, not just positive numbers

### What We Test
1. **Exact Calculations**
   - Total revenue: $25,000 (from mock data)
   - Product line grouping: Classic Cars ($15,800), Motorcycles ($6,400), Planes ($2,800)
   - Transaction count: 10 records
   - Distinct customers: 3 (Customer A, B, C)

2. **Stream Operations**
   - `groupingBy` + `summingDouble` for revenue aggregation
   - `sorted` + `limit` for top N queries
   - `filter` for high-value orders
   - `map` + `distinct` + `count` for unique customers

3. **Data Integrity**
   - Sum of status counts equals total transactions
   - Descending sort order verification
   - Threshold filtering accuracy
   - Format validation (e.g., quarter format "YYYY-Q#")

### Mock Data Structure
```java
10 records total
├── Classic Cars: 5 records, $15,800 revenue
├── Motorcycles: 3 records, $6,400 revenue
└── Planes: 2 records, $2,800 revenue

3 customers: Customer A, B, C
3 territories: NA, EMEA, APAC
2 years: 2023, 2024
1 high-value order: $10,000
```

**Test Results:**
```
Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
Time elapsed: 0.152s
```



