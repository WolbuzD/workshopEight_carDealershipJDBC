package com.pluralsight.dealership;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final DealershipManager dealershipManager;
    private final Scanner scanner;
    private Dealership currentDealership;

    public UserInterface() {
        this.dealershipManager = new DealershipManager();
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        selectDealership();

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> processGetByPriceRequest();
                case "2" -> processGetByMakeModelRequest();
                case "3" -> processGetByYearRequest();
                case "4" -> processGetByColorRequest();
                case "5" -> processGetByMileageRequest();
                case "6" -> processGetByVehicleTypeRequest();
                case "7" -> processGetAllVehiclesRequest();
                case "8" -> processAddVehicleRequest();
                case "9" -> processRemoveVehicleRequest();
                case "10" -> processSellLeaseVehicleRequest();
                case "99" -> {
                    System.out.println("Thank you for using the Car Dealership System!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void selectDealership() {
        System.out.println("=== Car Dealership System ===");
        System.out.println("Available Dealerships:");

        List<Dealership> dealerships = dealershipManager.getAllDealerships();

        if (dealerships.isEmpty()) {
            System.out.println("No dealerships found!");
            return;
        }

        for (int i = 0; i < dealerships.size(); i++) {
            System.out.println((i + 1) + ". " + dealerships.get(i));
        }

        System.out.print("Select a dealership (1-" + dealerships.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= dealerships.size()) {
                currentDealership = dealerships.get(choice - 1);
                System.out.println("Selected: " + currentDealership.getName());
            } else {
                System.out.println("Invalid selection. Using first dealership.");
                currentDealership = dealerships.get(0);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using first dealership.");
            currentDealership = dealerships.get(0);
        }
        System.out.println();
    }

    private void displayMenu() {
        System.out.println("\n=== " + currentDealership.getName() + " ===");
        System.out.println("1 - Find vehicles within a price range");
        System.out.println("2 - Find vehicles by make/model");
        System.out.println("3 - Find vehicles by year range");
        System.out.println("4 - Find vehicles by color");
        System.out.println("5 - Find vehicles by mileage range");
        System.out.println("6 - Find vehicles by type");
        System.out.println("7 - List ALL vehicles");
        System.out.println("8 - Add a vehicle");
        System.out.println("9 - Remove a vehicle");
        System.out.println("10 - Sell/lease a vehicle");
        System.out.println("99 - Quit");
        System.out.print("Enter your command: ");
    }

    private void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double minPrice = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter maximum price: ");
        double maxPrice = Double.parseDouble(scanner.nextLine().trim());

        List<Vehicle> vehicles = dealershipManager.getVehiclesByPriceRange(minPrice, maxPrice);
        displayVehicles(vehicles);
    }

    private void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter model: ");
        String model = scanner.nextLine().trim();

        List<Vehicle> vehicles = dealershipManager.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    private void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int minYear = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter maximum year: ");
        int maxYear = Integer.parseInt(scanner.nextLine().trim());

        List<Vehicle> vehicles = dealershipManager.getVehiclesByYearRange(minYear, maxYear);
        displayVehicles(vehicles);
    }

    private void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine().trim();

        List<Vehicle> vehicles = dealershipManager.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    private void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int minMileage = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter maximum mileage: ");
        int maxMileage = Integer.parseInt(scanner.nextLine().trim());

        List<Vehicle> vehicles = dealershipManager.getVehiclesByMileageRange(minMileage, maxMileage);
        displayVehicles(vehicles);
    }

    private void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type (e.g., SUV, Sedan, Truck): ");
        String type = scanner.nextLine().trim();

        List<Vehicle> vehicles = dealershipManager.getVehiclesByType(type);
        displayVehicles(vehicles);
    }

    private void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = dealershipManager.getVehiclesByDealership(currentDealership.getDealershipId());
        displayVehicles(vehicles);
    }

    private void processAddVehicleRequest() {
        System.out.println("Enter vehicle information:");

        System.out.print("VIN: ");
        String vin = scanner.nextLine().trim();

        System.out.print("Make: ");
        String make = scanner.nextLine().trim();

        System.out.print("Model: ");
        String model = scanner.nextLine().trim();

        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Color: ");
        String color = scanner.nextLine().trim();

        System.out.print("Mileage: ");
        int mileage = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        Vehicle vehicle = new Vehicle(vin, make, model, year, color, mileage, price, false);

        boolean success = dealershipManager.addVehicle(vehicle);
        if (success) {
            dealershipManager.addVehicleToDealership(currentDealership.getDealershipId(), vin);
            System.out.println("Vehicle added successfully!");
        } else {
            System.out.println("Failed to add vehicle.");
        }
    }

    private void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of vehicle to remove: ");
        String vin = scanner.nextLine().trim();

        Vehicle vehicle = dealershipManager.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        System.out.println("Vehicle to remove: " + vehicle);
        System.out.print("Are you sure you want to remove this vehicle? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes")) {
            dealershipManager.removeVehicleFromDealership(currentDealership.getDealershipId(), vin);
            boolean success = dealershipManager.removeVehicle(vin);
            if (success) {
                System.out.println("Vehicle removed successfully!");
            } else {
                System.out.println("Failed to remove vehicle.");
            }
        } else {
            System.out.println("Vehicle removal cancelled.");
        }
    }

    private void processSellLeaseVehicleRequest() {
        System.out.print("Enter VIN of vehicle to sell/lease: ");
        String vin = scanner.nextLine().trim();

        Vehicle vehicle = dealershipManager.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        if (vehicle.isSold()) {
            System.out.println("Vehicle is already sold!");
            return;
        }

        System.out.println("Selected vehicle: " + vehicle);
        System.out.println("1 - Sell");
        System.out.println("2 - Lease");
        System.out.print("Choose option: ");

        String option = scanner.nextLine().trim();

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();

        if (option.equals("1")) {
            processSale(vin, customerName, vehicle.getPrice());
        } else if (option.equals("2")) {
            processLease(vin, customerName);
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void processSale(String vin, String customerName, double vehiclePrice) {
        System.out.print("Enter sale price (or press Enter for list price $" + vehiclePrice + "): ");
        String priceInput = scanner.nextLine().trim();

        double salePrice = priceInput.isEmpty() ? vehiclePrice : Double.parseDouble(priceInput);

        SalesContract contract = new SalesContract(vin, customerName, LocalDate.now(), salePrice);

        boolean success = dealershipManager.createSalesContract(contract);
        if (success) {
            System.out.println("Sales contract created successfully!");
            System.out.println("Contract ID: " + contract.getContractId());
        } else {
            System.out.println("Failed to create sales contract.");
        }
    }

    private void processLease(String vin, String customerName) {
        System.out.print("Enter lease start date (YYYY-MM-DD) or press Enter for today: ");
        String startDateInput = scanner.nextLine().trim();

        LocalDate leaseStart = startDateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(startDateInput);

        System.out.print("Enter lease duration in months (default 36): ");
        String durationInput = scanner.nextLine().trim();

        int durationMonths = durationInput.isEmpty() ? 36 : Integer.parseInt(durationInput);
        LocalDate leaseEnd = leaseStart.plusMonths(durationMonths);

        System.out.print("Enter monthly payment: ");
        double monthlyPayment = Double.parseDouble(scanner.nextLine().trim());

        LeaseContract contract = new LeaseContract(vin, customerName, leaseStart, leaseEnd, monthlyPayment);

        boolean success = dealershipManager.createLeaseContract(contract);
        if (success) {
            System.out.println("Lease contract created successfully!");
            System.out.println("Lease ID: " + contract.getLeaseId());
        } else {
            System.out.println("Failed to create lease contract.");
        }
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found matching your criteria.");
            return;
        }

        System.out.println("\n=== Found " + vehicles.size() + " vehicles ===");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }
}