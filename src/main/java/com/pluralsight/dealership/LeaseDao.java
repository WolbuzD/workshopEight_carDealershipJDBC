package com.pluralsight.dealership;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaseDao {
    private final DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createLeaseContract(LeaseContract contract) {
        String sql = "INSERT INTO lease_contracts (VIN, customer_name, lease_start, lease_end, monthly_payment) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, contract.getVin());
            statement.setString(2, contract.getCustomerName());
            statement.setDate(3, Date.valueOf(contract.getLeaseStart()));
            statement.setDate(4, Date.valueOf(contract.getLeaseEnd()));
            statement.setDouble(5, contract.getMonthlyPayment());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Get the generated lease ID
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contract.setLeaseId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error creating lease contract: " + e.getMessage());
        }

        return false;
    }

    public List<LeaseContract> getLeaseContractsByDealership(int dealershipId) {
        String sql = """
            SELECT lc.* FROM lease_contracts lc
            JOIN inventory i ON lc.VIN = i.VIN
            WHERE i.dealership_id = ?
            ORDER BY lc.lease_start DESC
            """;

        List<LeaseContract> contracts = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LeaseContract contract = new LeaseContract(
                            resultSet.getInt("lease_id"),
                            resultSet.getString("VIN"),
                            resultSet.getString("customer_name"),
                            resultSet.getDate("lease_start").toLocalDate(),
                            resultSet.getDate("lease_end").toLocalDate(),
                            resultSet.getDouble("monthly_payment")
                    );
                    contracts.add(contract);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting lease contracts: " + e.getMessage());
        }

        return contracts;
    }

    public List<LeaseContract> getLeaseContractsByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM lease_contracts WHERE lease_start BETWEEN ? AND ? ORDER BY lease_start DESC";

        List<LeaseContract> contracts = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LeaseContract contract = new LeaseContract(
                            resultSet.getInt("lease_id"),
                            resultSet.getString("VIN"),
                            resultSet.getString("customer_name"),
                            resultSet.getDate("lease_start").toLocalDate(),
                            resultSet.getDate("lease_end").toLocalDate(),
                            resultSet.getDouble("monthly_payment")
                    );
                    contracts.add(contract);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting lease contracts by date range: " + e.getMessage());
        }

        return contracts;
    }

    public List<LeaseContract> getAllLeaseContracts() {
        String sql = "SELECT * FROM lease_contracts ORDER BY lease_start DESC";

        List<LeaseContract> contracts = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                LeaseContract contract = new LeaseContract(
                        resultSet.getInt("lease_id"),
                        resultSet.getString("VIN"),
                        resultSet.getString("customer_name"),
                        resultSet.getDate("lease_start").toLocalDate(),
                        resultSet.getDate("lease_end").toLocalDate(),
                        resultSet.getDouble("monthly_payment")
                );
                contracts.add(contract);
            }

        } catch (SQLException e) {
            System.err.println("Error getting all lease contracts: " + e.getMessage());
        }

        return contracts;
    }
}