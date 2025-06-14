package com.pluralsight.dealership;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/DealershipDatabase";
    private static final String DB_USERNAME = "root"; // Change to your MySQL username
    private static final String DB_PASSWORD = "Salimewol2242$"; // Change to your MySQL password

    private static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Connection pool settings
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(5);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}