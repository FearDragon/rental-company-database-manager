package com.github.feardragon.rental_company_database_manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ExpensesTableController implements Initializable {

    @FXML
    private TableColumn<ExpenseRow, Date> expenseDate;
    @FXML
    private TableColumn<ExpenseRow, String> expenseHouse;
    @FXML
    private TableColumn<ExpenseRow, Integer> expenseID;
    @FXML
    private TableColumn<ExpenseRow, String> expenseName;
    @FXML
    private TableColumn<ExpenseRow, BigDecimal> expensePrice;

    public void initialize(URL url, ResourceBundle rb) {
        expenseDate.setCellValueFactory(new PropertyValueFactory<ExpenseRow, Date>("expenseDate"));
        expenseHouse.setCellValueFactory(new PropertyValueFactory<ExpenseRow, String>("expenseHouse"));
        expenseID.setCellValueFactory(new PropertyValueFactory<ExpenseRow, Integer>("expenseID"));
        expenseName.setCellValueFactory(new PropertyValueFactory<ExpenseRow, String>("expenseName"));
        expensePrice.setCellValueFactory(new PropertyValueFactory<ExpenseRow, BigDecimal>("expensePrice"));
    }
}
