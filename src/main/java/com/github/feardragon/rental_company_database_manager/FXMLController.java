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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

    // Houses Table
    @FXML
    private TableView<HouseRow> housesTable;
    @FXML
    private HousesTableController housesTableController;

    // Enter entry menu
    @FXML
    private StackPane enterEntries;
    @FXML
    private VBox enterEntriesSideMenu;

    // Delete entry menu
    @FXML
    private StackPane deleteEntries;
    @FXML
    private VBox deleteEntriesSideMenu;

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

    // Expenses info
    @FXML
    private AnchorPane expensesInfo;
    @FXML
    private ExpensesInfoController expensesInfoController;

    // Income info
    @FXML
    private AnchorPane incomeInfo;
    @FXML
    private IncomeInfoController incomeInfoController;

    // Houses info
    @FXML
    private AnchorPane housesInfo;
    @FXML
    private HousesInfoController housesInfoController;

    // Delete Expenses
    @FXML
    private AnchorPane deleteExpenseMenu;
    @FXML
    private DeleteExpenseController deleteExpenseMenuController;

    // Delete Income
    @FXML
    private AnchorPane deleteIncomeMenu;
    @FXML
    private DeleteIncomeController deleteIncomeMenuController;

    // Delete Houses
    @FXML
    private AnchorPane deleteHousesMenu;
    @FXML
    private DeleteHouseController deleteHousesMenuController;

    public void setObjects(Expenses expenses, Income income, Houses houses) {
        enterExpenseMenuController.setObjects(expenses, income, houses);
        enterIncomeMenuController.setObjects(expenses, income, houses);
        enterHousesMenuController.setObjects(expenses, income, houses);
        expensesInfoController.setObjects(expenses, houses);
        incomeInfoController.setObjects(income, houses);
        deleteExpenseMenuController.setExpenses(expenses);
        deleteIncomeMenuController.setIncome(income);
        deleteHousesMenuController.setHouses(houses);

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
        expensesInfoController.setTable(expensesTable);
        incomeInfoController.setTable(incomeTable);
        housesInfoController.setTable(housesTable);
    }

    public void printIncome(ActionEvent event){
        incomeTable.setVisible(true);
        incomeInfo.setVisible(true);
        expensesTable.setVisible(false);
        expensesInfo.setVisible(false);
        housesTable.setVisible(false);
        housesInfo.setVisible(false);
    }

    public void printExpenses(ActionEvent event){
        expensesTable.setVisible(true);
        expensesInfo.setVisible(true);
        incomeTable.setVisible(false);
        incomeInfo.setVisible(false);
        housesTable.setVisible(false);
        housesInfo.setVisible(false);
    }

    public void printHouses(ActionEvent event){
        housesTable.setVisible(true);
        housesInfo.setVisible(true);
        incomeTable.setVisible(false);
        incomeInfo.setVisible(false);
        expensesTable.setVisible(false);
        expensesInfo.setVisible(false);
    }

    public void printEnterEntries(ActionEvent event){
        enterEntries.setVisible(true);
        enterEntriesSideMenu.setVisible(true);
        deleteEntries.setVisible(false);
        deleteEntriesSideMenu.setVisible(false);
    }

    public void printDeleteEntries(ActionEvent event){
        deleteEntries.setVisible(true);
        deleteEntriesSideMenu.setVisible(true);
        enterEntries.setVisible(false);
        enterEntriesSideMenu.setVisible(false);
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

    public void printDeleteExpenseMenu(ActionEvent event){
        deleteExpenseMenu.setVisible(true);
        deleteIncomeMenu.setVisible(false);
        deleteHousesMenu.setVisible(false);
    }

    public void printDeleteIncomeMenu(ActionEvent event){
        deleteIncomeMenu.setVisible(true);
        deleteExpenseMenu.setVisible(false);
        deleteHousesMenu.setVisible(false);
    }

    public void printDeleteHousesMenu(ActionEvent event){
        deleteHousesMenu.setVisible(true);
        deleteIncomeMenu.setVisible(false);
        deleteExpenseMenu.setVisible(false);
    }
}