package com.github.feardragon.rental_company_database_manager;

import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class Expenses {
    private static final String selectByMonth = "SELECT * FROM expenses WHERE MONTH(ExpenseDate) = ? AND YEAR(ExpenseDate) = ?";
    private static final String selectByMonthAndHouse = "SELECT * FROM expenses WHERE MONTH(ExpenseDate) = ? AND YEAR(ExpenseDate) = ? AND ExpenseHouseID = ?";
    private static final String selectByYear = "SELECT * FROM expenses WHERE YEAR(ExpenseDate) = ?";
    private static final String selectByYearAndHouse = "SELECT * FROM expenses WHERE YEAR(ExpenseDate) = ? AND ExpenseHouseID = ?";
    private static final String selectByID = "SELECT * FROM expenses WHERE Expense_id = ?";
    private static final String selectHouseFromHouseID = "SELECT * FROM houses WHERE House_id = ?";
    private static final String selectTable = "SELECT * FROM expenses";
    private static final String insertEntry = "INSERT INTO expenses (ExpenseHouseID, ExpenseName, ExpensePrice, ExpenseDate) values (?, ?, ?, ?)";
    private static final String deleteEntry = "DELETE FROM expenses WHERE Expense_id = ?";
    private static final String getLastEntry = "SELECT * FROM expenses ORDER BY Expense_id DESC LIMIT 1";

    HikariDataSource dataSource;

    private final ObservableList<ExpenseRow> table = FXCollections.observableArrayList();

    public Expenses(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ExpenseRow getEntryByID (int id) throws SQLException{
        int expenseID;
        String expenseHouse;
        String expenseName;
        BigDecimal expensePrice;
        Date expenseDate;
        ExpenseRow result;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByID)){
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()){
                rs.next();
                expenseID = rs.getInt(1);
                expenseHouse = getAddressByHouseID(rs.getInt(2));
                expenseName = rs.getString(3);
                expensePrice = rs.getBigDecimal(4);
                expenseDate = rs.getDate(5);
                result = new ExpenseRow(expenseID, expenseHouse, expenseName, expensePrice, expenseDate);
                return result;
            }
        }
    }

    public int getLastID() throws SQLException{
        int expenseID = -1;
        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(getLastEntry)){
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    expenseID = rs.getInt(1);
                }
            }
        }
        return expenseID;
    }

    public ExpenseRow enterEntry(int expenseHouseID, String expenseName, BigDecimal expensePrice, Date expenseDate) throws SQLException{
        ExpenseRow result;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertEntry)) {
            pstmt.setInt(1, expenseHouseID);
            pstmt.setString(2, expenseName);
            pstmt.setBigDecimal(3, expensePrice);
            pstmt.setDate(4, expenseDate);
            pstmt.executeUpdate();
            result = getEntryByID(getLastID());
            table.add(result);
        }
        return result;
    }

    public void deleteEntry(int expenseID) throws SQLException{
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteEntry)){
            pstmt.setInt(1, expenseID);
            pstmt.executeUpdate();
            table.remove(expenseID - 1);
        }
    }

    public ObservableList<ExpenseRow> getTable() throws SQLException{
        table.clear();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectTable)){
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()) {
                    table.add(new ExpenseRow(rs.getInt(1),
                            getAddressByHouseID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getBigDecimal(4),
                            rs.getDate(5)));
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

    public BigDecimal getYearlyExpense(int year) throws SQLException{
        BigDecimal result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByYear)){
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    result = result.add(rs.getBigDecimal(4));
                }
            }
        }
        return result;
    }

    public BigDecimal getMonthlyExpense(int month, int year) throws SQLException{
        BigDecimal result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonth)){
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

    public BigDecimal getYearlyExpenseByHouse(int year, int house) throws SQLException{
        BigDecimal result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByYearAndHouse)){
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

    public BigDecimal getMonthlyExpenseByHouse(int month, int year, int house) throws SQLException{
        BigDecimal  result = BigDecimal.ZERO;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectByMonthAndHouse)){
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
