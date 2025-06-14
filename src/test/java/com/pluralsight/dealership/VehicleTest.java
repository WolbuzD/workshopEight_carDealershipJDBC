// VehicleTest.java
package com.pluralsight.dealership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle("1HGBH41JXMN109186", "Honda", "Civic", 2021, "Blue", 15000, 22000.00, false);
    }

    @Test
    void testVehicleConstructor() {
        assertEquals("1HGBH41JXMN109186", vehicle.getVin());
        assertEquals("Honda", vehicle.getMake());
        assertEquals("Civic", vehicle.getModel());
        assertEquals(2021, vehicle.getYear());
        assertEquals("Blue", vehicle.getColor());
        assertEquals(15000, vehicle.getMileage());
        assertEquals(22000.00, vehicle.getPrice(), 0.01);
        assertFalse(vehicle.isSold());
    }

    @Test
    void testVehicleSetters() {
        vehicle.setVin("2HGBH41JXMN109187");
        vehicle.setMake("Toyota");
        vehicle.setModel("Camry");
        vehicle.setYear(2022);
        vehicle.setColor("Red");
        vehicle.setMileage(20000);
        vehicle.setPrice(25000.00);
        vehicle.setSold(true);

        assertEquals("2HGBH41JXMN109187", vehicle.getVin());
        assertEquals("Toyota", vehicle.getMake());
        assertEquals("Camry", vehicle.getModel());
        assertEquals(2022, vehicle.getYear());
        assertEquals("Red", vehicle.getColor());
        assertEquals(20000, vehicle.getMileage());
        assertEquals(25000.00, vehicle.getPrice(), 0.01);
        assertTrue(vehicle.isSold());
    }

    @Test
    void testToString() {
        String expected = "1HGBH41JXMN109186 | 2021 Honda Civic | Blue | 15,000 mi | $22000.00 | AVAILABLE";
        assertEquals(expected, vehicle.toString());

        vehicle.setSold(true);
        String expectedSold = "1HGBH41JXMN109186 | 2021 Honda Civic | Blue | 15,000 mi | $22000.00 | SOLD";
        assertEquals(expectedSold, vehicle.toString());
    }
}