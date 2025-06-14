package com.pluralsight.dealership;

public class Vehicle {
    private String vin;
    private String make;
    private String model;
    private int year;
    private String color;
    private int mileage;
    private double price;
    private boolean sold;

    public Vehicle(String vin, String make, String model, int year, String color, int mileage, double price, boolean sold) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.mileage = mileage;
        this.price = price;
        this.sold = sold;
    }

    // Getters and Setters
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isSold() { return sold; }
    public void setSold(boolean sold) { this.sold = sold; }

    @Override
    public String toString() {
        return String.format("%s | %d %s %s | %s | %,d mi | $%.2f | %s",
                vin, year, make, model, color, mileage, price, sold ? "SOLD" : "AVAILABLE");
    }
}