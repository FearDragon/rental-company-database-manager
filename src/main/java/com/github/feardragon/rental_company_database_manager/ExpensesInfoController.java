package com.github.feardragon.rental_company_database_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class ExpensesInfoController {

    @FXML
    private Label county;
    @FXML
    private Label email;
    @FXML
    private Label expenseDate;
    @FXML
    private Label expenseID;
    @FXML
    private Label expenseName;
    @FXML
    private Label expensePrice;
    @FXML
    private Label firstName;
    @FXML
    private Label houseAddress;
    @FXML
    private Label lastName;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label zipCode;
    @FXML
    private TableView<ExpenseRow> expensesTable;

    private Expenses expenses;
    private Houses houses;

    public void setObjects(Expenses expenses, Houses houses) {
        this.expenses = expenses;
        this.houses = houses;
    }

    public void setTable(TableView<ExpenseRow> expensesTable) {
        this.expensesTable = expensesTable;
        expensesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try{
                    expenseID.setText("Expense ID: " + newValue.getExpenseID());
                    expenseName.setText("Expense Name: " + newValue.getExpenseName());
                    expensePrice.setText("Expense Price: " + newValue.getExpensePrice());
                    expenseDate.setText("Expense Date: " + newValue.getExpenseDate());
                    houseAddress.setText("House Address: " + newValue.getExpenseHouse());
                    county.setText("County: " + houses.getCountyByID(expenses.getHouseIDByID(newValue.getExpenseID())));
                    zipCode.setText("ZIP Code: " + houses.getZipCodeByID(expenses.getHouseIDByID(newValue.getExpenseID())));
                    firstName.setText("Tenant First Name: " + houses.getFirstNameByID(expenses.getHouseIDByID(newValue.getExpenseID())));
                    lastName.setText("Tenant Last Name: " + houses.getLastNameByID(expenses.getHouseIDByID(newValue.getExpenseID())));
                    phoneNumber.setText("Tenant Phone Number: " + houses.getPhoneByID(expenses.getHouseIDByID(newValue.getExpenseID())));
                    email.setText("Tenant Email: " + houses.getEmailByID(expenses.getHouseIDByID(newValue.getExpenseID())));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
