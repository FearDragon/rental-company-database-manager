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
    private TableColumn<ExpenseRow, Date> expenseDate;
    @FXML
    private TableColumn<ExpenseRow, String> expenseHouse;
    @FXML
    private TableColumn<ExpenseRow, Integer> expenseID;
    @FXML
    private TableColumn<ExpenseRow, String> expenseName;
    @FXML
    private TableColumn<ExpenseRow, BigDecimal> expensePrice;
    @FXML
    private TableView<ExpenseRow> expensesTable;

    // Income Table
    @FXML
    private TableColumn<IncomeRow, Integer> incomeID;
    @FXML
    private TableColumn<IncomeRow, String> incomeHouse;
    @FXML
    private TableColumn<IncomeRow, String> incomeName;
    @FXML
    private TableColumn<IncomeRow, BigDecimal> incomeAmount;
    @FXML
    private TableColumn<IncomeRow, Date> datePaid;
    @FXML
    private TableColumn<IncomeRow, Date> dateDue;
    @FXML
    private TableView<IncomeRow> incomeTable;

    // Houses table
    @FXML
    private TableColumn<HouseRow, String> houseAddress;
    @FXML
    private TableColumn<HouseRow, String> houseCity;
    @FXML
    private TableColumn<HouseRow, String> houseCounty;
    @FXML
    private TableColumn<HouseRow, String> houseEmail;
    @FXML
    private TableColumn<HouseRow, String> houseFirstName;
    @FXML
    private TableColumn<HouseRow, Integer> houseID;
    @FXML
    private TableColumn<HouseRow, String> houseLastName;
    @FXML
    private TableColumn<HouseRow, String> housePhone;
    @FXML
    private TableColumn<HouseRow, String> houseState;
    @FXML
    private TableColumn<HouseRow, String> houseZip;
    @FXML
    private TableView<HouseRow> housesTable;

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
        expenseDate.setCellValueFactory(new PropertyValueFactory<ExpenseRow, Date>("expenseDate"));
        expenseHouse.setCellValueFactory(new PropertyValueFactory<ExpenseRow, String>("expenseHouse"));
        expenseID.setCellValueFactory(new PropertyValueFactory<ExpenseRow, Integer>("expenseID"));
        expenseName.setCellValueFactory(new PropertyValueFactory<ExpenseRow, String>("expenseName"));
        expensePrice.setCellValueFactory(new PropertyValueFactory<ExpenseRow, BigDecimal>("expensePrice"));

        incomeID.setCellValueFactory(new PropertyValueFactory<IncomeRow, Integer>("incomeID"));
        incomeHouse.setCellValueFactory(new PropertyValueFactory<IncomeRow, String>("incomeHouse"));
        incomeName.setCellValueFactory(new PropertyValueFactory<IncomeRow, String>("incomeName"));
        incomeAmount.setCellValueFactory(new PropertyValueFactory<IncomeRow, BigDecimal>("incomeAmount"));
        datePaid.setCellValueFactory(new PropertyValueFactory<IncomeRow, Date>("datePaid"));
        dateDue.setCellValueFactory(new PropertyValueFactory<IncomeRow, Date>("dateDue"));

        houseID.setCellValueFactory(new PropertyValueFactory<HouseRow, Integer>("houseID"));
        houseAddress.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("streetAddress"));
        houseCity.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("city"));
        houseCounty.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("county"));
        houseState.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("state"));
        houseZip.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("zipCode"));
        houseFirstName.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("firstName"));
        houseLastName.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("lastName"));
        houseEmail.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("email"));
        housePhone.setCellValueFactory(new PropertyValueFactory<HouseRow, String>("phoneNumber"));
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