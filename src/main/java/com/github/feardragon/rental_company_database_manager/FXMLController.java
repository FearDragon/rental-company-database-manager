package com.github.feardragon.rental_company_database_manager;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class FXMLController implements Initializable {

    // Expense Table
    @FXML
    private TableView<ExpenseRow> expensesTable;
    @FXML
    private ExpensesTableController expensesTableController;

    // Income Table
    @FXML
    private TableView<IncomeRow> incomeTable;
    @FXML
    private IncomeTableController incomeTableController;

    // Houses table
    @FXML
    private TableView<HouseRow> housesTable;
    @FXML
    private HousesTableController housesTableController;

    // Enter Expenses
    @FXML
    private AnchorPane enterExpenseMenu;
    @FXML
    private EnterExpenseController enterExpenseMenuController;

    // Enter Income
    @FXML
    private AnchorPane enterIncomeMenu;
    @FXML
    private EnterIncomeController enterIncomeMenuController;

    // Enter Houses
    @FXML
    private AnchorPane enterHousesMenu;
    @FXML
    private EnterHouseController enterHousesMenuController;

    public void setTables(Expenses expenses, Income income, Houses houses) {
        enterExpenseMenuController.setExpenses(expenses);
        enterExpenseMenuController.setIncome(income);
        enterExpenseMenuController.setHouses(houses);
        enterIncomeMenuController.setExpenses(expenses);
        enterIncomeMenuController.setIncome(income);
        enterIncomeMenuController.setHouses(houses);
        enterHousesMenuController.setExpenses(expenses);
        enterHousesMenuController.setIncome(income);
        enterHousesMenuController.setHouses(houses);

        try {
            expensesTable.setItems(expenses.getTable());
            incomeTable.setItems(income.getTable());
            housesTable.setItems(houses.getTable());
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void printIncome(ActionEvent event){
        incomeTable.setVisible(true);
        expensesTable.setVisible(false);
        housesTable.setVisible(false);
    }

    public void printExpenses(ActionEvent event){
        expensesTable.setVisible(true);
        incomeTable.setVisible(false);
        housesTable.setVisible(false);
    }

    public void printHouses(ActionEvent event){
        housesTable.setVisible(true);
        incomeTable.setVisible(false);
        expensesTable.setVisible(false);
    }

    public void printEnterExpenseMenu(ActionEvent event){
        enterExpenseMenu.setVisible(true);
        enterIncomeMenu.setVisible(false);
        enterHousesMenu.setVisible(false);
    }

    public void printEnterIncomeMenu(ActionEvent event){
        enterIncomeMenu.setVisible(true);
        enterExpenseMenu.setVisible(false);
        enterHousesMenu.setVisible(false);
    }

    public void printEnterHousesMenu(ActionEvent event){
        enterHousesMenu.setVisible(true);
        enterIncomeMenu.setVisible(false);
        enterExpenseMenu.setVisible(false);
    }
}