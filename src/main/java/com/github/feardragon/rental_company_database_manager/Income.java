package com.github.feardragon.rental_company_database_manager;

import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class Income {
    private static final String selectByMonthPaid = "SELECT * FROM income WHERE MONTH(DatePaid) = ?";
    private static final String selectByMonthDue = "SELECT * FROM income WHERE MONTH(DateDue) = ?";
    private static final String selectByMonthYearPaid = "SELECT * FROM income WHERE MONTH(DatePaid) = ? AND YEAR(DatePaid) = ?";
    private static final String selectByMonthYearDue = "SELECT * FROM income WHERE MONTH(DateDue) = ? AND YEAR(DateDue) = ?";
    private static final String selectByYearPaid = "SELECT * FROM income WHERE YEAR(DatePaid) = ?";
    private static final String selectByYearDue = "SELECT * FROM income WHERE YEAR(DateDue) = ?";
    private static final String selectByID = "SELECT * FROM income WHERE Income_id = ?";
    private static final String selectAddressFromHouseID = "SELECT * FROM houses WHERE House_id = ?";
    private static final String selectTable = "SELECT * FROM income";
    private static final String insertEntry = "INSERT INTO income (IncomeHouseID, IncomeName, IncomeAmount, DatePaid, DateDue) values (?, ?, ?, ?, ?)";
    private static final String getLastEntry = "SELECT * FROM income ORDER BY Income_id DESC LIMIT 1";
    HikariDataSource dataSource;

    public Income (HikariDataSource dataSource){
        this.dataSource = dataSource;
    }

    // Returns IncomeRow based on income_ID
    public IncomeRow getEntryByID (int id) throws SQLException{
        int incomeID;
        String incomeHouse;
        String incomeName;
        BigDecimal incomeAmount;
        Date datePaid;
        Date dateDue;
        IncomeRow result;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByID)){
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                rs.next();
                incomeID = rs.getInt(1);
                incomeHouse = getAddressByHouseID(rs.getInt(2));
                incomeName = rs.getString(3);
                incomeAmount = rs.getBigDecimal(4);
                datePaid = rs.getDate(5);
                dateDue = rs.getDate(6);
                result = new IncomeRow(incomeID, incomeHouse, incomeName, incomeAmount, datePaid, dateDue);
                return result;
            }
        }
    }

    public int getLastID() throws SQLException{
        int incomeID = -1;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(getLastEntry)){
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    incomeID = rs.getInt(1);
                }
            }
        }
        return incomeID;
    }

    public IncomeRow enterEntry(int incomeHouseID, String incomeName, BigDecimal incomeAmount, Date datePaid, Date dateDue) throws SQLException{
        IncomeRow result;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertEntry)) {
            pstmt.setInt(1, incomeHouseID);
            pstmt.setString(2, incomeName);
            pstmt.setBigDecimal(3, incomeAmount);
            pstmt.setDate(4, datePaid);
            pstmt.setDate(5, dateDue);
            pstmt.executeUpdate();
            result = getEntryByID(getLastID());
        }
        return result;
    }

    // Returns entire table
    public ObservableList<IncomeRow> getTable() throws SQLException{
        ObservableList<IncomeRow> list = FXCollections.observableArrayList();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectTable)){
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()) {
                    list.add(new IncomeRow(rs.getInt(1),
                            getAddressByHouseID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getBigDecimal(4),
                            rs.getDate(5),
                            rs.getDate(6)));
                }
            }
        }
        return  list;
    }

    public String getAddressByHouseID(int house) throws SQLException{
        String address = "";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectAddressFromHouseID)){
            pstmt.setInt(1, house);
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    address = rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(5) + " " + rs.getString(6);
                }
            }
        }
        return address;
    }

    // Returns an array list of integers containing the income_id of all entries of the inputted month and year
    public ArrayList<Integer> getIDByMonthYearPaid(int month, int year) throws SQLException{
        ArrayList<Integer> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonthYearPaid)){
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    results.add(rs.getInt(1));
                }
            }
        }
        return results;
    }

    public ArrayList<Integer> getIDByMonthYearDue(int month, int year) throws SQLException{
        ArrayList<Integer> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonthYearDue)){
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    results.add(rs.getInt(1));
                }
            }
        }
        return results;
    }

    public ArrayList<Integer> getIDByYearPaid (int year) throws SQLException{
        ArrayList<Integer> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByYearPaid)){
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    results.add(rs.getInt(1));
                }
            }
        }
        return results;
    }

    public ArrayList<Integer> getIDByYearDue (int year) throws SQLException{
        ArrayList<Integer> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByYearDue)){
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    results.add(rs.getInt(1));
                }
            }
        }
        return results;
    }

    public ArrayList<Integer> getIDByMonthPaid (int month) throws SQLException{
        ArrayList<Integer> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonthPaid)){
            pstmt.setInt(1, month);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    results.add(rs.getInt(1));
                }
            }
        }
        return results;
    }

    public ArrayList<Integer> getIDByMonthDue (int month) throws SQLException{
        ArrayList<Integer> results = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonthDue)){
            pstmt.setInt(1, month);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    results.add(rs.getInt(1));
                }
            }
        }
        return results;
    }
}
