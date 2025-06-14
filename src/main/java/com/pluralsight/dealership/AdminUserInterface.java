package com.pluralsight.dealership;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AdminUserInterface {
    private final DealershipManager dealershipManager;
    private final Scanner scanner;

    public AdminUserInterface() {
        this.dealershipManager = new DealershipManager();
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        System.out.println("=== Car Dealership Admin Panel ===");

        while (true) {
            displayAdminMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewAllSalesContracts();
                case "2" -> viewAllLeaseContracts();
                case "3" -> viewSalesContractsByDealership();
                case "4" -> viewLeaseContractsByDealership();
                case "5" -> viewContractsByDateRange();
                case "6" -> viewAllDealerships();
                case "7" -> viewAllVehicles();
                case "99" -> {
                    System.out.println("Thank you for using the Admin Panel!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayAdminMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1 - View all sales contracts");
        System.out.println("2 - View all lease contracts");
        System.out.println("3 - View sales contracts by dealership");
        System.out.println("4 - View lease contracts by dealership");
        System.out.println("5 - View contracts by date range");
        System.out.println("6 - View all dealerships");
        System.out.println("7 - View all vehicles");
        System.out.println("99 - Exit");
        System.out.print("Enter your choice: ");
    }

    private void viewAllSalesContracts() {
        List<SalesContract> contracts = dealershipManager.getAllSalesContracts();

        if (contracts.isEmpty()) {
            System.out.println("No sales contracts found.");
            return;
        }

        System.out.println("\n=== All Sales Contracts ===");
        double totalSales = 0;
        for (SalesContract contract : contracts) {
            System.out.println(contract);
            totalSales += contract.getSalePrice();
        }
        System.out.printf("Total Sales: $%.2f%n", totalSales);
        System.out.println("Total Contracts: " + contracts.size());
    }

    private void viewAllLeaseContracts() {
        List<LeaseContract> contracts = dealershipManager.getAllLeaseContracts();

        if (contracts.isEmpty()) {
            System.out.println("No lease contracts found.");
            return;
        }

        System.out.println("\n=== All Lease Contracts ===");
        double totalMonthlyPayments = 0;
        for (LeaseContract contract : contracts) {
            System.out.println(contract);
            totalMonthlyPayments += contract.getMonthlyPayment();
        }
        System.out.printf("Total Monthly Payments: $%.2f%n", totalMonthlyPayments);
        System.out.println("Total Contracts: " + contracts.size());
    }

    private void viewSalesContractsByDealership() {
        Dealership dealership = selectDealership();
        if (dealership == null) return;

        List<SalesContract> contracts = dealershipManager.getSalesContractsByDealership(dealership.getDealershipId());

        if (contracts.isEmpty()) {
            System.out.println("No sales contracts found for " + dealership.getName());
            return;
        }

        System.out.println("\n=== Sales Contracts for " + dealership.getName() + " ===");
        double totalSales = 0;
        for (SalesContract contract : contracts) {
            System.out.println(contract);
            totalSales += contract.getSalePrice();
        }
        System.out.printf("Total Sales: $%.2f%n", totalSales);
        System.out.println("Total Contracts: " + contracts.size());
    }

    private void viewLeaseContractsByDealership() {
        Dealership dealership = selectDealership();
        if (dealership == null) return;

        List<LeaseContract> contracts = dealershipManager.getLeaseContractsByDealership(dealership.getDealershipId());

        if (contracts.isEmpty()) {
            System.out.println("No lease contracts found for " + dealership.getName());
            return;
        }

        System.out.println("\n=== Lease Contracts for " + dealership.getName() + " ===");
        double totalMonthlyPayments = 0;
        for (LeaseContract contract : contracts) {
            System.out.println(contract);
            totalMonthlyPayments += contract.getMonthlyPayment();
        }
        System.out.printf("Total Monthly Payments: $%.2f%n", totalMonthlyPayments);
        System.out.println("Total Contracts: " + contracts.size());
    }

    private void viewContractsByDateRange() {
        System.out.println("Enter date range for contracts:");

        LocalDate startDate = getDateInput("Enter start date (YYYY-MM-DD): ");
        if (startDate == null) return;

        LocalDate endDate = getDateInput("Enter end date (YYYY-MM-DD): ");
        if (endDate == null) return;

        System.out.println("1 - Sales contracts");
        System.out.println("2 - Lease contracts");
        System.out.println("3 - Both");
        System.out.print("Choose contract type: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                List<SalesContract> salesContracts = dealershipManager.getSalesContractsByDateRange(startDate, endDate);
                displaySalesContractsWithSummary(salesContracts, "Sales Contracts from " + startDate + " to " + endDate);
            }
            case "2" -> {
                List<LeaseContract> leaseContracts = dealershipManager.getLeaseContractsByDateRange(startDate, endDate);
                displayLeaseContractsWithSummary(leaseContracts, "Lease Contracts from " + startDate + " to " + endDate);
            }
            case "3" -> {
                List<SalesContract> salesContracts = dealershipManager.getSalesContractsByDateRange(startDate, endDate);
                List<LeaseContract> leaseContracts = dealershipManager.getLeaseContractsByDateRange(startDate, endDate);

                displaySalesContractsWithSummary(salesContracts, "Sales Contracts from " + startDate + " to " + endDate);
                System.out.println();
                displayLeaseContractsWithSummary(leaseContracts, "Lease Contracts from " + startDate + " to " + endDate);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void viewAllDealerships() {
        List<Dealership> dealerships = dealershipManager.getAllDealerships();

        if (dealerships.isEmpty()) {
            System.out.println("No dealerships found.");
            return;
        }

        System.out.println("\n=== All Dealerships ===");
        for (Dealership dealership : dealerships) {
            System.out.println(dealership);

            // Show vehicle count for each dealership
            List<Vehicle> vehicles = dealershipManager.getVehiclesByDealership(dealership.getDealershipId());
            System.out.println("  - Available vehicles: " + vehicles.size());
        }
    }

    private void viewAllVehicles() {
        List<Vehicle> vehicles = dealershipManager.getAllAvailableVehicles();

        if (vehicles.isEmpty()) {
            System.