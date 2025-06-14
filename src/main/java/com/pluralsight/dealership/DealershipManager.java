package com.pluralsight.dealership;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

public class DealershipManager {
    private final DealershipDao dealershipDao;
    private final VehicleDao vehicleDao;
    private final SalesDao salesDao;
    private final LeaseDao leaseDao;

    public DealershipManager() {
        DataSource dataSource = DatabaseConfig.getDataSource();
        this.dealershipDao = new DealershipDao(dataSource);
        this.vehicleDao = new VehicleDao(dataSource);
        this.salesDao = new SalesDao(dataSource);
        this.leaseDao = new LeaseDao(dataSource);
    }

    // Dealership methods
    public List<Dealership> getAllDealerships() {
        return dealershipDao.getAllDealerships();
    }

    public Dealership getDealershipById(int dealershipId) {
        return dealershipDao.getDealershipById(dealershipId);
    }

    public List<Vehicle> getVehiclesByDealership(int dealershipId) {
        return dealershipDao.getVehiclesByDealership(dealershipId);
    }

    // Vehicle search methods
    public List<Vehicle> getVehiclesByPriceRange(double minPrice, double maxPrice) {
        return vehicleDao.getVehiclesByPriceRange(minPrice, maxPrice);
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return vehicleDao.getVehiclesByMakeModel(make, model);
    }

    public List<Vehicle> getVehiclesByYearRange(int minYear, int maxYear) {
        return vehicleDao.getVehiclesByYearRange(minYear, maxYear);
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        return vehicleDao.getVehiclesByColor(color);
    }

    public List<Vehicle> getVehiclesByMileageRange(int minMileage, int maxMileage) {
        return vehicleDao.getVehiclesByMileageRange(minMileage, maxMileage);
    }

    public List<Vehicle> getVehiclesByType(String type) {
        return vehicleDao.getVehiclesByType(type);
    }

    public List<Vehicle> getAllAvailableVehicles() {
        return vehicleDao.getAllAvailableVehicles();
    }

    public Vehicle getVehicleByVin(String vin) {
        return vehicleDao.getVehicleByVin(vin);
    }

    // Vehicle management methods
    public boolean addVehicle(Vehicle vehicle) {
        return vehicleDao.addVehicle(vehicle);
    }

    public boolean removeVehicle(String vin) {
        return vehicleDao.removeVehicle(vin);
    }

    public boolean addVehicleToDealership(int dealershipId, String vin) {
        return dealershipDao.addVehicleToDealership(dealershipId, vin);
    }

    public boolean removeVehicleFromDealership(int dealershipId, String vin) {
        return dealershipDao.removeVehicleFromDealership(dealershipId, vin);
    }

    // Contract methods
    public boolean createSalesContract(SalesContract contract) {
        boolean success = salesDao.createSalesContract(contract);
        if (success) {
            // Mark vehicle as sold
            vehicleDao.markVehicleAsSold(contract.getVin());
        }
        return success;
    }

    public boolean createLeaseContract(LeaseContract contract) {
        return leaseDao.createLeaseContract(contract);
    }

    public List<SalesContract> getSalesContractsByDealership(int dealershipId) {
        return salesDao.getSalesContractsByDealership(dealershipId);
    }

    public List<LeaseContract> getLeaseContractsByDealership(int dealershipId) {
        return leaseDao.getLeaseContractsByDealership(dealershipId);
    }

    public List<SalesContract> getSalesContractsByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesDao.getSalesContractsByDateRange(startDate, endDate);
    }

    public List<LeaseContract> getLeaseContractsByDateRange(LocalDate startDate, LocalDate endDate) {
        return leaseDao.getLeaseContractsByDateRange(startDate, endDate);
    }

    public List<SalesContract> getAllSalesContracts() {
        return salesDao.getAllSalesContracts();
    }

    public List<LeaseContract> getAllLeaseContracts() {
        return leaseDao.getAllLeaseContracts();
    }
}