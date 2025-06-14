package com.pluralsight.dealership;

public class Dealership {
    private int dealershipId;
    private String name;
    private String address;
    private String phone;

    public Dealership(int dealershipId, String name, String address, String phone) {
        this.dealershipId = dealershipId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public int getDealershipId() { return dealershipId; }
    public void setDealershipId(int dealershipId) { this.dealershipId = dealershipId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", name, address, phone);
    }
}