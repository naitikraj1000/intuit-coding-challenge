package com.example.dataanalysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SalesAnalysis class demonstrating testing of Stream operations.
 * Uses mock data for predictable, fast testing.
 */
class SalesAnalysisTest {

    private SalesAnalysis analysis;
    private List<SalesRecord> mockData;

    @BeforeEach
    void setUp() {
        // Create mock test data with known values
        mockData = createMockSalesData();
        analysis = new SalesAnalysis(mockData);
    }

    /**
     * Creates mock sales data for testing.
     * Total: 10 records
     * Total Revenue: $25,000
     * Product Lines: Classic Cars, Motorcycles, Planes
     * Territories: NA, EMEA, APAC
     * Customers: Customer A, B, C
     */
    private List<SalesRecord> createMockSalesData() {
        List<SalesRecord> data = new ArrayList<>();
        
        // Classic Cars - NA - Customer A
        data.add(new SalesRecord(1001, 10, 100.0, 1000.0, 
            LocalDate.of(2023, 1, 15), "Shipped", 1, 1, 2023,
            "Classic Cars", "C001", "Customer A", "New York", "USA", "NA", "Small"));
        
        data.add(new SalesRecord(1002, 20, 150.0, 3000.0, 
            LocalDate.of(2023, 2, 20), "Shipped", 1, 2, 2023,
            "Classic Cars", "C002", "Customer A", "New York", "USA", "NA", "Medium"));
        
        // Motorcycles - EMEA - Customer B
        data.add(new SalesRecord(1003, 5, 200.0, 1000.0, 
            LocalDate.of(2023, 3, 10), "Shipped", 1, 3, 2023,
            "Motorcycles", "M001", "Customer B", "Paris", "France", "EMEA", "Small"));
        
        data.add(new SalesRecord(1004, 15, 300.0, 4500.0, 
            LocalDate.of(2023, 4, 5), "Shipped", 2, 4, 2023,
            "Motorcycles", "M002", "Customer B", "Paris", "France", "EMEA", "Large"));
        
        // Planes - APAC - Customer C
        data.add(new SalesRecord(1005, 8, 250.0, 2000.0, 
            LocalDate.of(2023, 5, 12), "Shipped", 2, 5, 2023,
            "Planes", "P001", "Customer C", "Tokyo", "Japan", "APAC", "Medium"));
        
        data.add(new SalesRecord(1006, 12, 100.0, 1200.0, 
            LocalDate.of(2023, 6, 18), "Cancelled", 2, 6, 2023,
            "Classic Cars", "C003", "Customer A", "New York", "USA", "NA", "Small"));
        
        data.add(new SalesRecord(1007, 6, 150.0, 900.0, 
            LocalDate.of(2024, 1, 8), "Shipped", 1, 1, 2024,
            "Motorcycles", "M003", "Customer C", "Tokyo", "Japan", "APAC", "Small"));
        
        data.add(new SalesRecord(1008, 10, 80.0, 800.0, 
            LocalDate.of(2024, 2, 14), "On Hold", 1, 2, 2024,
            "Planes", "P002", "Customer B", "Paris", "France", "EMEA", "Small"));
        
        data.add(new SalesRecord(1009, 3, 200.0, 600.0, 
            LocalDate.of(2024, 3, 22), "Shipped", 1, 3, 2024,
            "Classic Cars", "C004", "Customer C", "Tokyo", "Japan", "APAC", "Small"));
        
        // High value order
        data.add(new SalesRecord(1010, 100, 100.0, 10000.0, 
            LocalDate.of(2024, 4, 30), "Shipped", 2, 4, 2024,
            "Classic Cars", "C005", "Customer A", "New York", "USA", "NA", "Large"));
        
        return data;
    }

    @Test
    @DisplayName("Total revenue calculation")
    void testGetTotalRevenue() {
        double totalRevenue = analysis.getTotalRevenue();
        
        // Mock data total: 1000 + 3000 + 1000 + 4500 + 2000 + 1200 + 900 + 800 + 600 + 10000 = 25000
        assertEquals(25000.0, totalRevenue, 0.01, "Total revenue should be $25,000");
    }

    @Test
    @DisplayName("Revenue grouping by product line")
    void testGetRevenueByProductLine() {
        Map<String, Double> revenueByProductLine = analysis.getRevenueByProductLine();
        
        assertEquals(3, revenueByProductLine.size(), "Should have 3 product lines");
        
        // Classic Cars: 1000 + 3000 + 1200 + 600 + 10000 = 15800
        assertEquals(15800.0, revenueByProductLine.get("Classic Cars"), 0.01);
        
        // Motorcycles: 1000 + 4500 + 900 = 6400
        assertEquals(6400.0, revenueByProductLine.get("Motorcycles"), 0.01);
        
        // Planes: 2000 + 800 = 2800
        assertEquals(2800.0, revenueByProductLine.get("Planes"), 0.01);
    }

    @Test
    @DisplayName("Revenue grouping by territory")
    void testGetRevenueByTerritory() {
        Map<String, Double> revenueByTerritory = analysis.getRevenueByTerritory();
        
        assertFalse(revenueByTerritory.isEmpty(), "Revenue by territory should not be empty");
        
        revenueByTerritory.values().forEach(revenue ->
                assertTrue(revenue > 0, "Each territory should have positive revenue"));
    }

    @Test
    @DisplayName("Revenue grouping by deal size")
    void testGetRevenueByDealSize() {
        Map<String, Double> revenueByDealSize = analysis.getRevenueByDealSize();
        
        assertFalse(revenueByDealSize.isEmpty(), "Revenue by deal size should not be empty");
    }

    @Test
    @DisplayName("Top customers sorting and limiting")
    void testGetTopCustomers() {
        int topN = 10;
        List<Map.Entry<String, Double>> topCustomers = analysis.getTopCustomers(topN);
        
        assertNotNull(topCustomers, "Top customers list should not be null");
        assertTrue(topCustomers.size() <= topN, "Should return at most " + topN + " customers");
        
        // Verify descending order
        for (int i = 0; i < topCustomers.size() - 1; i++) {
            assertTrue(topCustomers.get(i).getValue() >= topCustomers.get(i + 1).getValue(),
                    "Customers should be sorted in descending order by revenue");
        }
    }

    @Test
    @DisplayName("Top countries sorting and limiting")
    void testGetTopCountries() {
        int topN = 10;
        List<Map.Entry<String, Double>> topCountries = analysis.getTopCountries(topN);
        
        assertNotNull(topCountries, "Top countries list should not be null");
        assertTrue(topCountries.size() <= topN, "Should return at most " + topN + " countries");
        
        // Verify descending order
        for (int i = 0; i < topCountries.size() - 1; i++) {
            assertTrue(topCountries.get(i).getValue() >= topCountries.get(i + 1).getValue(),
                    "Countries should be sorted in descending order by revenue");
        }
    }

    @Test
    @DisplayName("Average order value calculation")
    void testGetAverageOrderValue() {
        double avgOrderValue = analysis.getAverageOrderValue();
        
        assertTrue(avgOrderValue > 0, "Average order value should be positive");
        
        double totalRevenue = analysis.getTotalRevenue();
        long totalTransactions = analysis.getTotalTransactions();
        double calculatedAvg = totalRevenue / totalTransactions;
        
        assertEquals(calculatedAvg, avgOrderValue, 0.01,
                "Average should equal total revenue divided by transaction count");
    }

    @Test
    @DisplayName("Total transactions count")
    void testGetTotalTransactions() {
        long totalTransactions = analysis.getTotalTransactions();
        
        assertEquals(10, totalTransactions, "Should have exactly 10 transactions");
    }

    @Test
    @DisplayName("Quantity aggregation by product line")
    void testGetQuantityByProductLine() {
        Map<String, Integer> quantityByProductLine = analysis.getQuantityByProductLine();
        
        assertFalse(quantityByProductLine.isEmpty(), "Quantity by product line should not be empty");
        
        quantityByProductLine.values().forEach(quantity ->
                assertTrue(quantity > 0, "Each product line should have positive quantity"));
    }

    @Test
    @DisplayName("Revenue aggregation by year")
    void testGetRevenueByYear() {
        Map<Integer, Double> revenueByYear = analysis.getRevenueByYear();
        
        assertFalse(revenueByYear.isEmpty(), "Revenue by year should not be empty");
        
        // Verify years are reasonable
        revenueByYear.keySet().forEach(year ->
                assertTrue(year >= 2000 && year <= 2100, "Year should be reasonable"));
        
        revenueByYear.values().forEach(revenue ->
                assertTrue(revenue > 0, "Each year should have positive revenue"));
    }

    @Test
    @DisplayName("High value orders filtering")
    void testGetHighValueOrders() {
        double threshold = 5000.0;
        List<SalesRecord> highValueOrders = analysis.getHighValueOrders(threshold);
        
        assertEquals(1, highValueOrders.size(), "Should have 1 order above $5000");
        
        // Verify the one high-value order
        assertEquals(10000.0, highValueOrders.get(0).getSales(), 0.01);
        assertEquals(1010, highValueOrders.get(0).getOrderNumber());
        
        // Test with lower threshold
        List<SalesRecord> mediumOrders = analysis.getHighValueOrders(3000.0);
        assertEquals(2, mediumOrders.size(), "Should have 2 orders above $3000");
        
        // Verify descending order
        assertTrue(mediumOrders.get(0).getSales() >= mediumOrders.get(1).getSales());
    }

    @Test
    @DisplayName("Distinct customer count")
    void testGetDistinctCustomerCount() {
        long distinctCustomers = analysis.getDistinctCustomerCount();
        
        assertEquals(3, distinctCustomers, "Should have exactly 3 distinct customers (A, B, C)");
    }

    @Test
    @DisplayName("Order status distribution")
    void testGetOrderStatusDistribution() {
        Map<String, Long> statusDistribution = analysis.getOrderStatusDistribution();
        
        assertFalse(statusDistribution.isEmpty(), "Status distribution should not be empty");
        
        long totalCount = statusDistribution.values().stream().mapToLong(Long::longValue).sum();
        assertEquals(analysis.getTotalTransactions(), totalCount,
                "Sum of status counts should equal total transactions");
    }
}
