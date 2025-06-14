package com.pluralsight.dealership;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesDao {
    private final DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createSalesContract(SalesContract contract) {
        String sql = "INSERT INTO sales_contracts (VIN, customer_name, sale_date, sale_price) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, contract.getVin());
            statement.setString(2, contract.getCustomerName());
            statement.setDate(3, Date.valueOf(contract.getSaleDate()));
            statement.setDouble(4, contract.getSalePrice());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Get the generated contract ID
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contract.setContractId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error creating sales contract: " + e.getMessage());
        }

        return false;
    }

    public List<SalesContract> getSalesContractsByDealership(int dealershipId) {
        String sql = """
            SELECT sc.* FROM sales_contracts sc
            JOIN inventory i ON sc.VIN = i.VIN
            WHERE i.dealership_id = ?
            ORDER BY sc.sale_date DESC
            """;

        List<SalesContract> contracts = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, dealershipId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SalesContract contract = new SalesContract(
                            resultSet.getInt("contract_id"),
                            resultSet.getString("VIN"),
                            resultSet.getString("customer_name"),
                            resultSet.getDate("sale_date").toLocalDate(),
                            resultSet.getDouble("sale_price")
                    );
                    contracts.add(contract);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting sales contracts: " + e.getMessage());
        }

        return contracts;
    }

    public List<SalesContract> getSalesContractsByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM sales_contracts WHERE sale_date BETWEEN ? AND ? ORDER BY sale_date DESC";

        List<SalesContract> contracts = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SalesContract contract = new SalesContract(
                            resultSet.getInt("contract_id"),
                            resultSet.getString("VIN"),
                            resultSet.getString("customer_name"),
                            resultSet.getDate("sale_date").toLocalDate(),
                            resultSet.getDouble("sale_price")
                    );
                    contracts.add(contract);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting sales contracts by date range: " + e.getMessage());
        }

        return contracts;
    }

    public List<SalesContract> getAllSalesContracts() {
        String sql = "SELECT * FROM sales_contracts ORDER BY sale_date DESC";

        List<SalesContract> contracts = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                SalesContract contract = new SalesContract(
                        resultSet.getInt("contract_id"),
                        resultSet.getString("VIN"),
                        resultSet.getString("customer_name"),
                        resultSet.getDate("sale_date").toLocalDate(),
                        resultSet.getDouble("sale_price")
                );
                contracts.add(contract);
            }

        } catch (SQLException e) {
            System.err.println("Error getting all sales contracts: " + e.getMessage());
        }

        return contracts;
    }
}