package com.pluralsight.dealership;

import java.time.LocalDate;

public class LeaseContract {
    private int leaseId;
    private String vin;
    private String customerName;
    private LocalDate leaseStart;
    private LocalDate leaseEnd;
    private double monthlyPayment;

    public LeaseContract(int leaseId, String vin, String customerName, LocalDate leaseStart, LocalDate leaseEnd, double monthlyPayment) {
        this.leaseId = leaseId;
        this.vin = vin;
        this.customerName = customerName;
        this.leaseStart = leaseStart;
        this.leaseEnd = leaseEnd;
        this.monthlyPayment = monthlyPayment;
    }

    public LeaseContract(String vin, String customerName, LocalDate leaseStart, LocalDate leaseEnd, double monthlyPayment) {
        this.vin = vin;
        this.customerName = customerName;
        this.leaseStart = leaseStart;
        this.leaseEnd = leaseEnd;
        this.monthlyPayment = monthlyPayment;
    }

    // Getters and Setters
    public int getLeaseId() { return leaseId; }
    public void setLeaseId(int leaseId) { this.leaseId = leaseId; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDate getLeaseStart() { return leaseStart; }
    public void setLeaseStart(LocalDate leaseStart) { this.leaseStart = leaseStart; }

    public LocalDate getLeaseEnd() { return leaseEnd; }
    public void setLeaseEnd(LocalDate leaseEnd) { this.leaseEnd = leaseEnd; }

    public double getMonthlyPayment() { return monthlyPayment; }
    public void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    @Override
    public String toString() {
        return String.format("Lease Contract #%d | VIN: %s | Customer: %s | %s to %s | Monthly: $%.2f",
                leaseId, vin, customerName, leaseStart, leaseEnd, monthlyPayment);
    }
}