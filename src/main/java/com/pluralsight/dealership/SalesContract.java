package com.pluralsight.dealership;

import java.time.LocalDate;

public class SalesContract {
    private int contractId;
    private String vin;
    private String customerName;
    private LocalDate saleDate;
    private double salePrice;

    public SalesContract(int contractId, String vin, String customerName, LocalDate saleDate, double salePrice) {
        this.contractId = contractId;
        this.vin = vin;
        this.customerName = customerName;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
    }

    public SalesContract(String vin, String customerName, LocalDate saleDate, double salePrice) {
        this.vin = vin;
        this.customerName = customerName;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
    }

    // Getters and Setters
    public int getContractId() { return contractId; }
    public void setContractId(int contractId) { this.contractId = contractId; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

    public double getSalePrice() { return salePrice; }
    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }

    @Override
    public String toString() {
        return String.format("Sale Contract #%d | VIN: %s | Customer: %s | Date: %s | Price: $%.2f",
                contractId, vin, customerName, saleDate, salePrice);
    }
}