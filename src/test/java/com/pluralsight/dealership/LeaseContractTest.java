// LeaseContractTest.java
package com.pluralsight.dealership;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class LeaseContractTest {
    private LeaseContract leaseContract;
    private LocalDate leaseStart;
    private LocalDate leaseEnd;

    @BeforeEach
    void setUp() {
        leaseStart = LocalDate.of(2024, 6, 15);
        leaseEnd = LocalDate.of(2027, 6, 15);
        leaseContract = new LeaseContract(1, "1HGBH41JXMN109186", "John Doe", leaseStart, leaseEnd, 350.00);
    }

    @Test
    void testLeaseContractConstructorWithId() {
        assertEquals(1, leaseContract.getLeaseId());
        assertEquals("1HGBH41JXMN109186", leaseContract.getVin());
        assertEquals("John Doe", leaseContract.getCustomerName());
        assertEquals(leaseStart, leaseContract.getLeaseStart());
        assertEquals(leaseEnd, leaseContract.getLeaseEnd());
        assertEquals(350.00, leaseContract.getMonthlyPayment(), 0.01);
    }

    @Test
    void testLeaseContractConstructorWithoutId() {
        LeaseContract contract = new LeaseContract("2HGBH41JXMN109187", "Jane Smith", leaseStart, leaseEnd, 400.00);

        assertEquals("2HGBH41JXMN109187", contract.getVin());
        assertEquals("Jane Smith", contract.getCustomerName());
        assertEquals(leaseStart, contract.getLeaseStart());
        assertEquals(leaseEnd, contract.getLeaseEnd());
        assertEquals(400.00, contract.getMonthlyPayment(), 0.01);
    }

    @Test
    void testLeaseContractSetters() {
        leaseContract.setLeaseId(2);
        leaseContract.setVin("2HGBH41JXMN109187");
        leaseContract.setCustomerName("Jane Smith");
        LocalDate newStart = LocalDate.of(2024, 7, 1);
        LocalDate newEnd = LocalDate.of(2027, 7, 1);
        leaseContract.setLeaseStart(newStart);
        leaseContract.setLeaseEnd(newEnd);
        leaseContract.setMonthlyPayment(400.00);

        assertEquals(2, leaseContract.getLeaseId());
        assertEquals("2HGBH41JXMN109187", leaseContract.getVin());
        assertEquals("Jane Smith", leaseContract.getCustomerName());
        assertEquals(newStart, leaseContract.getLeaseStart());
        assertEquals(newEnd, leaseContract.getLeaseEnd());
        assertEquals(400.00, leaseContract.getMonthlyPayment(), 0.01);
    }

    @Test
    void testToString() {
        String expected = "Lease Contract #1 | VIN: 1HGBH41JXMN109186 | Customer: John Doe | 2024-06-15 to 2027-06-15 | Monthly: $350.00";
        assertEquals(expected, leaseContract.toString());
    }
}