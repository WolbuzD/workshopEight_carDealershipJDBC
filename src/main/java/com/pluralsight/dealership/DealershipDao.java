package com.pluralsight.dealership;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealershipDao {
    private final DataSource dataSource;

    public DealershipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dealership> getAllDealerships() {
        String sql = "SELECT * FROM dealerships";
        List<Dealership> dealerships = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Dealership dealership = new Dealership(
                        resultSet.getInt("dealership_id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")
                );
                dealerships.add(dealership);
            }

        } catch (SQLException e) {
            System.err.println("Error getting dealerships: " + e.getMessage());
        }

        return dealerships;
    }

    public Dealership getDealershipById(int dealershipId) {
        String sql = "SELECT * FROM dealerships WHERE dealership_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Dealership(
                            resultSet.getInt("dealership_id"),
                            resultSet.getString("name"),
                            resultSet.getString("address"),
                            resultSet.getString("phone")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting dealership by ID: " + e.getMessage());
        }

        return null;
    }

    public List<Vehicle> getVehiclesByDealership(int dealershipId) {
        String sql = """
            SELECT v.* FROM vehicles v
            JOIN inventory i ON v.VIN = i.VIN
            WHERE i.dealership_id = ? AND v.sold = FALSE
            """;

        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = new Vehicle(
                            resultSet.getString("VIN"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            resultSet.getString("color"),
                            resultSet.getInt("mileage"),
                            resultSet.getDouble("price"),
                            resultSet.getBoolean("sold")
                    );
                    vehicles.add(vehicle);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting vehicles by dealership: " + e.getMessage());
        }

        return vehicles;
    }

    public boolean addVehicleToDealership(int dealershipId, String vin) {
        String sql = "INSERT INTO inventory (dealership_id, VIN) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);
            statement.setString(2, vin);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding vehicle to dealership: " + e.getMessage());
            return false;
        }
    }

    public boolean removeVehicleFromDealership(int dealershipId, String vin) {
        String sql = "DELETE FROM inventory WHERE dealership_id = ? AND VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);
            statement.setString(2, vin);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error removing vehicle from dealership: " + e.getMessage());
            return false;
        }
    }
}