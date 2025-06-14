// SalesContractTest.java
package com.pluralsight.dealership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class SalesContractTest {
    private SalesContract salesContract;
    private LocalDate saleDate;

    @BeforeEach
    void setUp() {
        saleDate = LocalDate.of(2024, 6, 15);
        salesContract = new SalesContract(1, "1HGBH41JXMN109186", "John Doe", saleDate, 22000.00);
    }

    @Test
    void testSalesContractConstructorWithId() {
        assertEquals(1, salesContract.getContractId());
        assertEquals("1HGBH41JXMN109186", salesContract.getVin());
        assertEquals("John Doe", salesContract.getCustomerName());
        assertEquals(saleDate, salesContract.getSaleDate());
        assertEquals(22000.00, salesContract.getSalePrice(), 0.01);
    }

    @Test
    void testSalesContractConstructorWithoutId() {
        SalesContract contract = new SalesContract("2HGBH41JXMN109187", "Jane Smith", saleDate, 25000.00);

        assertEquals("2HGBH41JXMN109187", contract.getVin());
        assertEquals("Jane Smith", contract.getCustomerName());
        assertEquals(saleDate, contract.getSaleDate());
        assertEquals(25000.00, contract.getSalePrice(), 0.01);
    }

    @Test
    void testSalesContractSetters() {
        salesContract.setContractId(2);
        salesContract.setVin("2HGBH41JXMN109187");
        salesContract.setCustomerName("Jane Smith");
        LocalDate newDate = LocalDate.of(2024, 7, 1);
        salesContract.setSaleDate(newDate);
        salesContract.setSalePrice(25000.00);

        assertEquals(2, salesContract.getContractId());
        assertEquals("2HGBH41JXMN109187", salesContract.getVin());
        assertEquals("Jane Smith", salesContract.getCustomerName());
        assertEquals(newDate, salesContract.getSaleDate());
        assertEquals(25000.00, salesContract.getSalePrice(), 0.01);
    }

    @Test
    void testToString() {
        String expected = "Sale Contract #1 | VIN: 1HGBH41JXMN109186 | Customer: John Doe | Date: 2024-06-15 | Price: $22000.00";
        assertEquals(expected, salesContract.toString());
    }
}
