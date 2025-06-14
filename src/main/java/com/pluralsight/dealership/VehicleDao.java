package com.pluralsight.dealership;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private final DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Phase 1 - Search methods
    public List<Vehicle> getVehiclesByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ? AND sold = FALSE";
        return executeVehicleQuery(sql, minPrice, maxPrice);
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        String sql = "SELECT * FROM vehicles WHERE make = ? AND model = ? AND sold = FALSE";
        return executeVehicleQuery(sql, make, model);
    }

    public List<Vehicle> getVehiclesByYearRange(int minYear, int maxYear) {
        String sql = "SELECT * FROM vehicles WHERE year BETWEEN ? AND ? AND sold = FALSE";
        return executeVehicleQuery(sql, minYear, maxYear);
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        String sql = "SELECT * FROM vehicles WHERE color = ? AND sold = FALSE";
        return executeVehicleQuery(sql, color);
    }

    public List<Vehicle> getVehiclesByMileageRange(int minMileage, int maxMileage) {
        String sql = "SELECT * FROM vehicles WHERE mileage BETWEEN ? AND ? AND sold = FALSE";
        return executeVehicleQuery(sql, minMileage, maxMileage);
    }

    public List<Vehicle> getVehiclesByType(String type) {
        // Note: In your schema, there's no vehicle type column, so we'll search by model
        String sql = "SELECT * FROM vehicles WHERE model LIKE ? AND sold = FALSE";
        return executeVehicleQuery(sql, "%" + type + "%");
    }

    public List<Vehicle> getAllAvailableVehicles() {
        String sql = "SELECT * FROM vehicles WHERE sold = FALSE";
        return executeVehicleQuery(sql);
    }

    public Vehicle getVehicleByVin(String vin) {
        String sql = "SELECT * FROM vehicles WHERE VIN = ?";
        List<Vehicle> vehicles = executeVehicleQuery(sql, vin);
        return vehicles.isEmpty() ? null : vehicles.get(0);
    }

    // Phase 2 - Add and remove vehicles
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (VIN, make, model, year, color, mileage, price, sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vehicle.getVin());
            statement.setString(2, vehicle.getMake());
            statement.setString(3, vehicle.getModel());
            statement.setInt(4, vehicle.getYear());
            statement.setString(5, vehicle.getColor());
            statement.setInt(6, vehicle.getMileage());
            statement.setDouble(7, vehicle.getPrice());
            statement.setBoolean(8, vehicle.isSold());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean removeVehicle(String vin) {
        String sql = "DELETE FROM vehicles WHERE VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vin);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error removing vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean markVehicleAsSold(String vin) {
        String sql = "UPDATE vehicles SET sold = TRUE WHERE VIN = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, vin);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error marking vehicle as sold: " + e.getMessage());
            return false;
        }
    }

    // Helper method to execute queries and map results to Vehicle objects
    private List<Vehicle> executeVehicleQuery(String sql, Object... parameters) {
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set parameters
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

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
            System.err.println("Error executing vehicle query: " + e.getMessage());
        }

        return vehicles;
    }
}