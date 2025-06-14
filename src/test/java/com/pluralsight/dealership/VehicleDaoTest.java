// VehicleDaoTest.java (Mock/Integration Test)
package com.pluralsight.dealership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleDaoTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private VehicleDao vehicleDao;

    @BeforeEach
    void setUp() throws SQLException {
        vehicleDao = new VehicleDao(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void testGetVehiclesByPriceRange() throws SQLException {
        // Setup mock data
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("VIN")).thenReturn("1HGBH41JXMN109186");
        when(resultSet.getString("make")).thenReturn("Honda");
        when(resultSet.getString("model")).thenReturn("Civic");
        when(resultSet.getInt("year")).thenReturn(2021);
        when(resultSet.getString("color")).thenReturn("Blue");
        when(resultSet.getInt("mileage")).thenReturn(15000);
        when(resultSet.getDouble("price")).thenReturn(22000.00);
        when(resultSet.getBoolean("sold")).thenReturn(false);

        List<Vehicle> vehicles = vehicleDao.getVehiclesByPriceRange(20000.00, 25000.00);

        assertFalse(vehicles.isEmpty());
        assertEquals(1, vehicles.size());
        Vehicle vehicle = vehicles.get(0);
        assertEquals("1HGBH41JXMN109186", vehicle.getVin());
        assertEquals("Honda", vehicle.getMake());
        assertEquals("Civic", vehicle.getModel());

        verify(preparedStatement).setObject(1, 20000.00);
        verify(preparedStatement).setObject(2, 25000.00);
    }

    @Test
    void testAddVehicle() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Vehicle vehicle = new Vehicle("1HGBH41JXMN109186", "Honda", "Civic", 2021, "Blue", 15000, 22000.00, false);
        boolean result = vehicleDao.addVehicle(vehicle);

        assertTrue(result);
        verify(preparedStatement).setString(1, "1HGBH41JXMN109186");
        verify(preparedStatement).setString(2, "Honda");
        verify(preparedStatement).setString(3, "Civic");
        verify(preparedStatement).setInt(4, 2021);
        verify(preparedStatement).setString(5, "Blue");
        verify(preparedStatement).setInt(6, 15000);
        verify(preparedStatement).setDouble(7, 22000.00);
        verify(preparedStatement).setBoolean(8, false);
    }

    @Test
    void testRemoveVehicle() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = vehicleDao.removeVehicle("1HGBH41JXMN109186");

        assertTrue(result);
        verify(preparedStatement).setString(1, "1HGBH41JXMN109186");
    }

    @Test
    void testMarkVehicleAsSold() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = vehicleDao.markVehicleAsSold("1HGBH41JXMN109186");

        assertTrue(result);
        verify(preparedStatement).setString(1, "1HGBH41JXMN109186");
    }

    @Test
    void testGetVehicleByVin() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("VIN")).thenReturn("1HGBH41JXMN109186");
        when(resultSet.getString("make")).thenReturn("Honda");
        when(resultSet.getString("model")).thenReturn("Civic");
        when(resultSet.getInt("year")).thenReturn(2021);
        when(resultSet.getString("color")).thenReturn("Blue");
        when(resultSet.getInt("mileage")).thenReturn(15000);
        when(resultSet.getDouble("price")).thenReturn(22000.00);
        when(resultSet.getBoolean("sold")).thenReturn(false);

        Vehicle vehicle = vehicleDao.getVehicleByVin("1HGBH41JXMN109186");

        assertNotNull(vehicle);
        assertEquals("1HGBH41JXMN109186", vehicle.getVin());
        assertEquals("Honda", vehicle.getMake());
        verify(preparedStatement).setObject(1, "1HGBH41JXMN109186");
    }

    @Test
    void testGetVehicleByVinNotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        Vehicle vehicle = vehicleDao.getVehicleByVin("NONEXISTENT");

        assertNull(vehicle);
    }
}