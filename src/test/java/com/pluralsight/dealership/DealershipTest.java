// DealershipTest.java
package com.pluralsight.dealership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class DealershipTest {
    private Dealership dealership;

    @BeforeEach
    void setUp() {
        dealership = new Dealership(1, "Honda of Downtown", "123 Main St, City, State", "555-0123");
    }

    @Test
    void testDealershipConstructor() {
        assertEquals(1, dealership.getDealershipId());
        assertEquals("Honda of Downtown", dealership.getName());
        assertEquals("123 Main St, City, State", dealership.getAddress());
        assertEquals("555-0123", dealership.getPhone());
    }

    @Test
    void testDealershipSetters() {
        dealership.setDealershipId(2);
        dealership.setName("Toyota Center");
        dealership.setAddress("456 Oak Ave, City, State");
        dealership.setPhone("555-0456");

        assertEquals(2, dealership.getDealershipId());
        assertEquals("Toyota Center", dealership.getName());
        assertEquals("456 Oak Ave, City, State", dealership.getAddress());
        assertEquals("555-0456", dealership.getPhone());
    }

    @Test
    void testToString() {
        String expected = "Honda of Downtown | 123 Main St, City, State | 555-0123";
        assertEquals(expected, dealership.toString());
    }
}