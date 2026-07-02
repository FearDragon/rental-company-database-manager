package com.github.feardragon.rental_company_database_manager;

import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;

public class Houses {
    private final static String selectTable = "SELECT * FROM houses";
    private static final String selectByID = "SELECT * FROM houses WHERE House_id = ?";
    private static final String insertEntry = "INSERT INTO houses (StreetAddress, City, County, State, ZipCode, FirstName, LastName, Email, PhoneNumber) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String getLastEntry = "SELECT * FROM houses ORDER BY House_id DESC LIMIT 1";
    HikariDataSource dataSource;

    public Houses(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Returns HouseRow based on income_ID
    public HouseRow getEntryByID (int id) throws SQLException{
        int houseID;
        String streetAddress;
        String city;
        String county;
        String state;
        String zipCode;
        String firstName;
        String lastName;
        String email;
        String phoneNumber;
        HouseRow result;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByID)){
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                rs.next();
                houseID = rs.getInt(1);
                streetAddress = rs.getString(2);
                city = rs.getString(3);
                county = rs.getString(4);
                state = rs.getString(5);
                zipCode = rs.getString(6);
                firstName = rs.getString(7);
                lastName = rs.getString(8);
                email = rs.getString(9);
                phoneNumber = rs.getString(10);
                result = new HouseRow(houseID, streetAddress, city, county, state, zipCode, firstName, lastName, email, phoneNumber);
                return result;
            }
        }
    }

    public int getLastID() throws SQLException{
        int houseID = -1;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(getLastEntry)){
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    houseID = rs.getInt(1);
                }
            }
        }
        return houseID;
    }

    public HouseRow enterEntry(String streetAddress, String city, String county, String state, String zipCode, String firstName, String lastName, String email, String phoneNumber) throws SQLException{
        HouseRow result;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertEntry)) {
            pstmt.setString(1, streetAddress);
            pstmt.setString(2, city);
            pstmt.setString(3, county);
            pstmt.setString(4, state);
            pstmt.setString(5, zipCode);
            pstmt.setString(6, firstName);
            pstmt.setString(7, lastName);
            pstmt.setString(8, email);
            pstmt.setString(9, phoneNumber);
            pstmt.executeUpdate();
            result = getEntryByID(getLastID());
        }
        return result;
    }

    public ObservableList<HouseRow> getTable() throws SQLException {
        ObservableList<HouseRow> list = FXCollections.observableArrayList();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectTable)){
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()) {
                    list.add(new HouseRow(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9),
                            rs.getString(10)));
                }
            }
        }
        return  list;
    }

    public ObservableList<String> getAddresses() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectTable)){
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()) {
                    list.add(rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(5) + " "  + rs.getString(6));
                }
            }
        }
        return list;
    }
}
