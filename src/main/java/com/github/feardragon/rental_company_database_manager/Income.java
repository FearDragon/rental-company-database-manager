package com.github.feardragon.rental_company_database_manager;

import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class Income {
    private static final String selectByMonthPaid = "SELECT * FROM income WHERE MONTH(DatePaid) = ? AND YEAR(DatePaid) = ?";
    private static final String selectByMonthDue = "SELECT * FROM income WHERE MONTH(DateDue) = ? AND YEAR(DateDue) = ?";
    private static final String selectByMonthPaidAndHouse = "SELECT * FROM income WHERE MONTH(DatePaid) = ? AND YEAR(DatePaid) = ? AND IncomeHouseID = ?";
    private static final String selectByYearPaid = "SELECT * FROM income WHERE YEAR(DatePaid) = ?";
    private static final String selectByYearDue = "SELECT * FROM income WHERE YEAR(DateDue) = ?";
    private static final String selectByYearPaidAndHouse = "SELECT * FROM income WHERE YEAR(DatePaid) = ? AND IncomeHouseID = ?";
    private static final String selectByID = "SELECT * FROM income WHERE Income_id = ?";
    private static final String selectHouseFromHouseID = "SELECT * FROM houses WHERE House_id = ?";
    private static final String selectTable = "SELECT * FROM income";
    private static final String insertEntry = "INSERT INTO income (IncomeHouseID, IncomeName, IncomeAmount, DatePaid, DateDue) values (?, ?, ?, ?, ?)";
    private static final String deleteEntry = "DELETE FROM income WHERE Income_id = ?";
    private static final String getLastEntry = "SELECT * FROM income ORDER BY Income_id DESC LIMIT 1";

    HikariDataSource dataSource;

    private final ObservableList<IncomeRow> table = FXCollections.observableArrayList();

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
            table.add(result);
        }
        return result;
    }

    public void deleteEntry(int incomeID) throws SQLException{
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteEntry)){
            pstmt.setInt(1, incomeID);
            pstmt.executeUpdate();
            table.remove(incomeID - 1);
        }
    }

    // Returns entire table
    public ObservableList<IncomeRow> getTable() throws SQLException{
        table.clear();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectTable)){
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()) {
                    table.add(new IncomeRow(rs.getInt(1),
                            getAddressByHouseID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getBigDecimal(4),
                            rs.getDate(5),
                            rs.getDate(6)));
                }
            }
        }
        return table;
    }

    public int getHouseIDByID(int id) throws SQLException{
        int results = -1;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByID)){
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    results = rs.getInt(2);
                }
            }
        }
        return results;
    }

    public String getAddressByHouseID(int house) throws SQLException{
        String address = "";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectHouseFromHouseID)){
            pstmt.setInt(1, house);
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    address = rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(5) + " " + rs.getString(6);
                }
            }
        }
        return address;
    }

    public BigDecimal getYearlyIncome(int year) throws SQLException{
        BigDecimal result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByYearPaid)){
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    result = result.add(rs.getBigDecimal(4));
                }
            }
        }
        return result;
    }

    public BigDecimal getMonthlyIncome(int month, int year) throws SQLException{
        BigDecimal result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonthPaid)){
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    result = result.add(rs.getBigDecimal(4));
                }
            }
        }
        return result;
    }

    public BigDecimal getYearlyIncomeByHouse(int year, int house) throws SQLException{
        BigDecimal result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByYearPaidAndHouse)){
            pstmt.setInt(1, year);
            pstmt.setInt(2, house);
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    result = result.add(rs.getBigDecimal(4));
                }
            }
        }
        return result;
    }

    public BigDecimal getMonthlyIncomeByHouse(int month, int year, int house) throws SQLException{
        BigDecimal result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonthPaidAndHouse)){
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            pstmt.setInt(3, house);
            try (ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    result = result.add(rs.getBigDecimal(4));
                }
            }
        }
        return result;
    }
}
