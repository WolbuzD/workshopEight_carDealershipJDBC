// Test Suite Runner
        package com.pluralsight.dealership;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        VehicleTest.class,
        DealershipTest.class,
        SalesContractTest.class,
        LeaseContractTest.class,
        VehicleDaoTest.class,
        DealershipManagerTest.class
})
public class DealershipTestSuite {
    // Test suite runner
}