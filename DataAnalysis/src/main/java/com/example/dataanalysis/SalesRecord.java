package com.example.dataanalysis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Class to hold a single sales record from CSV
public class SalesRecord {
    private final int orderNumber;
    private final int quantityOrdered;
    private final double priceEach;
    private final double sales;
    private final LocalDate orderDate;
    private final String status;
    private final int qtrId;
    private final int monthId;
    private final int yearId;
    private final String productLine;
    private final String productCode;
    private final String customerName;
    private final String city;
    private final String country;
    private final String territory;
    private final String dealSize;

    public SalesRecord(int orderNumber, int quantityOrdered, double priceEach, double sales,
            LocalDate orderDate, String status, int qtrId, int monthId, int yearId,
            String productLine, String productCode, String customerName,
            String city, String country, String territory, String dealSize) {
        this.orderNumber = orderNumber;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
        this.sales = sales;
        this.orderDate = orderDate;
        this.status = status;
        this.qtrId = qtrId;
        this.monthId = monthId;
        this.yearId = yearId;
        this.productLine = productLine;
        this.productCode = productCode;
        this.customerName = customerName;
        this.city = city;
        this.country = country;
        this.territory = territory;
        this.dealSize = dealSize;
    }

    // Create a SalesRecord from a CSV line
    public static SalesRecord fromCsvLine(String line) {
        String[] fields = parseCsvLine(line);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm");
        LocalDate orderDate = LocalDate.parse(fields[4], formatter);

        return new SalesRecord(
                Integer.parseInt(fields[0]), // ORDERNUMBER
                Integer.parseInt(fields[1]), // QUANTITYORDERED
                Double.parseDouble(fields[2]), // PRICEEACH
                Double.parseDouble(fields[3]), // SALES
                orderDate, // ORDERDATE
                fields[5], // STATUS
                Integer.parseInt(fields[6]), // QTR_ID
                Integer.parseInt(fields[7]), // MONTH_ID
                Integer.parseInt(fields[8]), // YEAR_ID
                fields[9], // PRODUCTLINE
                fields[11], // PRODUCTCODE
                fields[12], // CUSTOMERNAME
                fields[15], // CITY
                fields[17], // COUNTRY
                fields[18], // TERRITORY
                fields[21] // DEALSIZE
        );
    }

    // Split CSV line, handling commas inside quotes
    private static String[] parseCsvLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    // Getters
    public int getOrderNumber() {
        return orderNumber;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public double getPriceEach() {
        return priceEach;
    }

    public double getSales() {
        return sales;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public int getQtrId() {
        return qtrId;
    }

    public int getMonthId() {
        return monthId;
    }

    public int getYearId() {
        return yearId;
    }

    public String getProductLine() {
        return productLine;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getTerritory() {
        return territory;
    }

    public String getDealSize() {
        return dealSize;
    }

    @Override
    public String toString() {
        return String.format("Order %d: %s - %s ($%.2f) - %s, %s",
                orderNumber, productLine, customerName, sales, city, country);
    }
}
