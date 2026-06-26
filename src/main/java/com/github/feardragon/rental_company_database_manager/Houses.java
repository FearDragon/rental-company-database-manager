package com.github.feardragon.rental_company_database_manager;

import com.zaxxer.hikari.HikariDataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Houses {
    private final static String selectTable = "SELECT * FROM houses";
    HikariDataSource dataSource;

    public Houses(HikariDataSource dataSource) {
        this.dataSource = dataSource;
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
