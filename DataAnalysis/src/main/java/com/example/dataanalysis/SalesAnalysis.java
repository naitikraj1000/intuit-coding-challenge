package com.example.dataanalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Main class for analyzing sales data
public class SalesAnalysis {

        private List<SalesRecord> salesData;

        // Regular constructor that loads from file
        public SalesAnalysis(String csvPath) throws IOException {
                this.salesData = loadSalesData(csvPath);
        }

        // Constructor for testing with pre-loaded data
        public SalesAnalysis(List<SalesRecord> salesData) {
                this.salesData = salesData;
        }

        // Read CSV and convert to list of records
        private List<SalesRecord> loadSalesData(String csvPath) throws IOException {
                try (Stream<String> lines = Files.lines(Paths.get(csvPath),
                                java.nio.charset.StandardCharsets.ISO_8859_1)) {
                        return lines.skip(1) // Skip header
                                        .map(SalesRecord::fromCsvLine)
                                        .collect(Collectors.toList());
                }
        }

        // Get total revenue from all sales
        public double getTotalRevenue() {
                return salesData.stream().mapToDouble(SalesRecord::getSales).sum();
        }

        // Revenue grouped by product line
        public Map<String, Double> getRevenueByProductLine() {
                return salesData.stream().collect(Collectors.groupingBy(SalesRecord::getProductLine,
                                Collectors.summingDouble(SalesRecord::getSales)));
        }

        // Revenue by territory
        public Map<String, Double> getRevenueByTerritory() {
                return salesData.stream().collect(Collectors.groupingBy(SalesRecord::getTerritory,
                                Collectors.summingDouble(SalesRecord::getSales)));
        }

        // Revenue grouped by deal size (small/medium/large)
        public Map<String, Double> getRevenueByDealSize() {
                return salesData.stream().collect(Collectors.groupingBy(SalesRecord::getDealSize,
                                Collectors.summingDouble(SalesRecord::getSales)));
        }

        // Get top N customers sorted by revenue
        public List<Map.Entry<String, Double>> getTopCustomers(int n) {
                return salesData.stream()
                                .collect(Collectors.groupingBy(
                                                SalesRecord::getCustomerName,
                                                Collectors.summingDouble(SalesRecord::getSales)))
                                .entrySet()
                                .stream()
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                                .limit(n)
                                .collect(Collectors.toList());
        }

        // Top N countries by revenue
        public List<Map.Entry<String, Double>> getTopCountries(int n) {
                return salesData.stream()
                                .collect(Collectors.groupingBy(
                                                SalesRecord::getCountry,
                                                Collectors.summingDouble(SalesRecord::getSales)))
                                .entrySet()
                                .stream()
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                                .limit(n)
                                .collect(Collectors.toList());
        }

        // Average value per order
        public double getAverageOrderValue() {
                return salesData.stream().mapToDouble(SalesRecord::getSales).average().orElse(0.0);
        }

        // Just count how many transactions we have
        public long getTotalTransactions() {
                return salesData.size();
        }

        // Total quantity sold for each product line
        public Map<String, Integer> getQuantityByProductLine() {
                return salesData.stream().collect(Collectors.groupingBy(SalesRecord::getProductLine,
                                Collectors.summingInt(SalesRecord::getQuantityOrdered)));
        }

        // Revenue broken down by year
        public Map<Integer, Double> getRevenueByYear() {
                return salesData.stream().collect(Collectors.groupingBy(SalesRecord::getYearId,
                                Collectors.summingDouble(SalesRecord::getSales)));
        }

        // Filter orders above a certain value
        public List<SalesRecord> getHighValueOrders(double threshold) {
                return salesData.stream().filter(record -> record.getSales() > threshold)
                                .sorted(Comparator.comparingDouble(SalesRecord::getSales).reversed())
                                .collect(Collectors.toList());
        }

        // Count unique customers
        public long getDistinctCustomerCount() {
                return salesData.stream().map(SalesRecord::getCustomerName).distinct().count();
        }

        // How many orders for each status (shipped, cancelled, etc)
        public Map<String, Long> getOrderStatusDistribution() {
                return salesData.stream().collect(Collectors.groupingBy(SalesRecord::getStatus, Collectors.counting()));
        }

        public void printAnalysis() {
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║              Sales Data Analysis - Java Streams Demonstration             ║");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
                System.out.println();

                printBasicStats();
                printRevenueByProductLine();
                printRevenueByTerritory();
                printRevenueByDealSize();
                printTopCustomers(10);
                printTopCountries(10);
                printRevenueByYear();
                printQuantityByProductLine();
                printOrderStatusDistribution();
                printHighValueOrders(10000.0, 5);

                System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║                         Analysis Complete                                 ║");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
        }

        private void printBasicStats() {
                System.out.println("1. TOTAL REVENUE");
                System.out.println("   " + "─".repeat(70));
                System.out.printf("   Total Revenue: $%,.2f\n", getTotalRevenue());
                System.out.printf("   Total Transactions: %,d\n", getTotalTransactions());
                System.out.printf("   Average Order Value: $%,.2f\n", getAverageOrderValue());
                System.out.printf("   Distinct Customers: %,d\n", getDistinctCustomerCount());
                System.out.println();
        }

        private void printRevenueByProductLine() {
                System.out.println("2. REVENUE BY PRODUCT LINE");
                System.out.println("   " + "─".repeat(70));
                getRevenueByProductLine().entrySet().stream()
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                                .forEach(e -> System.out.printf("   %-30s $%,12.2f\n", e.getKey() + ":", e.getValue()));
                System.out.println();
        }

        private void printRevenueByTerritory() {
                System.out.println("3. REVENUE BY TERRITORY");
                System.out.println("   " + "─".repeat(70));
                getRevenueByTerritory().entrySet().stream()
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                                .forEach(e -> System.out.printf("   %-30s $%,12.2f\n", e.getKey() + ":", e.getValue()));
                System.out.println();
        }

        private void printRevenueByDealSize() {
                System.out.println("4. REVENUE BY DEAL SIZE");
                System.out.println("   " + "─".repeat(70));
                getRevenueByDealSize().entrySet().stream()
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                                .forEach(e -> System.out.printf("   %-30s $%,12.2f\n", e.getKey() + ":", e.getValue()));
                System.out.println();
        }

        private void printTopCustomers(int n) {
                System.out.println("5. TOP " + n + " CUSTOMERS BY REVENUE");
                System.out.println("   " + "─".repeat(70));
                getTopCustomers(n).forEach(e -> System.out.printf("   %-50s $%,10.2f\n", e.getKey(), e.getValue()));
                System.out.println();
        }

        private void printTopCountries(int n) {
                System.out.println("6. TOP " + n + " COUNTRIES BY REVENUE");
                System.out.println("   " + "─".repeat(70));
                getTopCountries(n)
                                .forEach(e -> System.out.printf("   %-30s $%,12.2f\n", e.getKey() + ":", e.getValue()));
                System.out.println();
        }

        private void printRevenueByYear() {
                System.out.println("7. REVENUE BY YEAR");
                System.out.println("   " + "─".repeat(70));
                getRevenueByYear().entrySet().stream().sorted(Map.Entry.comparingByKey())
                                .forEach(e -> System.out.printf("   %d: $%,12.2f\n", e.getKey(), e.getValue()));
                System.out.println();
        }

        private void printQuantityByProductLine() {
                System.out.println("8. QUANTITY SOLD BY PRODUCT LINE");
                System.out.println("   " + "─".repeat(70));
                getQuantityByProductLine().entrySet().stream()
                                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                                .forEach(e -> System.out.printf("   %-30s %,8d units\n", e.getKey() + ":",
                                                e.getValue()));
                System.out.println();
        }

        private void printOrderStatusDistribution() {
                System.out.println("9. ORDER STATUS DISTRIBUTION");
                System.out.println("   " + "─".repeat(70));
                getOrderStatusDistribution().entrySet().stream()
                                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEach(e -> System.out
                                                .printf("   %-30s %,8d orders\n", e.getKey() + ":", e.getValue()));
                System.out.println();
        }

        private void printHighValueOrders(double threshold, int limit) {
                System.out.println(
                                "10. HIGH VALUE ORDERS (> $" + String.format("%,.0f", threshold) + ") - Top " + limit);
                System.out.println("    " + "─".repeat(70));
                List<SalesRecord> orders = getHighValueOrders(threshold);
                orders.stream()
                                .limit(limit)
                                .forEach(record -> System.out.printf(
                                                "    Order #%-8d | %-25s | %-20s | $%,10.2f\n",
                                                record.getOrderNumber(),
                                                record.getProductLine(),
                                                record.getCustomerName().substring(0,
                                                                Math.min(20, record.getCustomerName().length())),
                                                record.getSales()));
                System.out.printf("    Total high-value orders: %d\n", orders.size());
                System.out.println();
        }

        public static void main(String[] args) {
                try {
                        String csvPath = "src/main/resources/sales.csv";
                        SalesAnalysis analysis = new SalesAnalysis(csvPath);
                        analysis.printAnalysis();
                } catch (IOException e) {
                        System.err.println("Error reading CSV file: " + e.getMessage());
                        e.printStackTrace();
                }
        }
}
