package com.github.feardragon.rental_company_database_manager;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> expenseHouseComboBox;
    @FXML
    private TextField expenseNameField;
    @FXML
    private TextField expensePriceField;
    @FXML
    private DatePicker expenseDateField;

    // Enter Income
    @FXML
    private AnchorPane enterIncomeMenu;
    @FXML
    private ComboBox<String> incomeHouseComboBox;
    @FXML
    private TextField incomeNameField;
    @FXML
    private TextField incomeAmountField;
    @FXML
    private DatePicker incomeDatePaid;
    @FXML
    private DatePicker incomeDateDue;

    // Enter Houses
    ObservableList<String> states = FXCollections.observableArrayList(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
            "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
            "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
            "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
    @FXML
    private AnchorPane enterHousesMenu;
    @FXML
    private ComboBox<String> housesStateComboBox;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField housesEmailField;
    @FXML
    private TextField housesPhoneField;
    @FXML
    private TextField housesStreetField;
    @FXML
    private TextField housesCityField;
    @FXML
    private TextField housesCountyField;
    @FXML
    private TextField housesFirstNameField;
    @FXML
    private TextField housesLastNameField;


    private Expenses expenses;
    private Income income;
    private Houses houses;

    public void setTables(Expenses expenses, Income income, Houses houses) {
        this.expenses = expenses;
        this.income = income;
        this.houses = houses;

        try {
            expensesTable.setItems(expenses.getTable());
            incomeTable.setItems(income.getTable());
            housesTable.setItems(houses.getTable());
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOptions() throws SQLException {
        expenseHouseComboBox.getItems().addAll(houses.getAddresses());
        incomeHouseComboBox.getItems().addAll(houses.getAddresses());
        housesStateComboBox.getItems().addAll(states);

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

        // Formats money field
        UnaryOperator<TextFormatter.Change> moneyFilter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("^\\d*(\\.\\d{0,2})?$") ? change : null;
        };
        // Formats zip code field
        UnaryOperator<TextFormatter.Change> zipFilter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("^\\d{0,5}(-\\d{0,4})?$") ? change : null;
        };
        // Formats phone number field
        UnaryOperator<TextFormatter.Change> phoneFilter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("^\\+?[0-9\\s\\-]*$") ? change : null;
        };
        setupMoneyField(expensePriceField, moneyFilter);
        setupMoneyField(incomeAmountField, moneyFilter);
        setupPhoneField(housesPhoneField, phoneFilter);
        zipCodeField.setTextFormatter(new TextFormatter(zipFilter));
        housesEmailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if(!housesEmailField.getText().contains("@") || !housesEmailField.getText().contains(".")) {
                    System.out.println("Please enter a valid email address");
                }
            }
        });
    }

    public void handleExpensePriceAction() {
        formatMoneyField(expensePriceField);
    }

    public void handleIncomeAmountAction() {
        formatMoneyField(incomeAmountField);
    }

    public void handleHousesPhoneAction() {
        formatPhoneField(housesPhoneField);
    }

    // Sets up the format filter the money field and adds 0's to the end when clicking off the field
    private void setupMoneyField(TextField field, UnaryOperator<TextFormatter.Change> filter) {
        field.setTextFormatter(new TextFormatter<>(filter));
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                formatMoneyField(field);
            }
        });
    }
    // Sets up the phone number field and formats it by sectioning the number and adding "-" and "+"
    private void setupPhoneField(TextField field, UnaryOperator<TextFormatter.Change> filter) {
        field.setTextFormatter(new TextFormatter<>(filter));
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                formatPhoneField(field);
            }
        });
    }

    // Formats phone number by adding "-" and "+" for sectioning and country code
    private void formatPhoneField(TextField field) {
        String text = field.getText().trim();
        if (text.isEmpty()) {
            return;
        }

        String cleanText = text.replaceAll("[^0-9+]", "");
        String digitsOnly = text.replaceAll("[^0-9]", "");
        String prefix = "";
        String localDigits = cleanText;

        if (digitsOnly.length() > 10) {
            int prefixLength = cleanText.length() - 10;
            prefix = cleanText.substring(0, prefixLength).trim();
            localDigits = cleanText.substring(prefixLength);
            prefix = prefix + " ";
            if (!prefix.startsWith("+") && !prefix.isEmpty()) {
                prefix = "+" +  prefix;
            }
        } else if (cleanText.startsWith("+")) {
            field.setText(cleanText);
            prefix = prefix + " ";
            return;
        }
        int length = localDigits.length();
        String formattedNumber;

        if (length <= 3) {
            formattedNumber = localDigits;
        } else if (length <= 6) {
            formattedNumber = localDigits.substring(0, 3) + "-" + localDigits.substring(3);
        } else {
            formattedNumber = localDigits.substring(0, 3) + "-" +
                    localDigits.substring(3, 6) + "-" +
                    localDigits.substring(6);
        }

        field.setText(prefix + formattedNumber);
    }

    // Adds 0's to end of money amount
    private void formatMoneyField(TextField field){
        String text = field.getText().trim();
        if (text.isEmpty()){
            return;
        }
        if (!text.contains(".")) {
            field.setText(text + ".00");
        } else if (text.endsWith(".")) {
            field.setText(text + "00");
        } else {
            String[] parts = text.split("\\.");
            if (parts.length > 1 && parts[1].length() == 1) {
                field.setText(text + "0");
            }
        }
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

    public void enterExpense(ActionEvent event){
        int houseID;
        String expenseName;
        BigDecimal expensePrice;
        Date expenseDate;
        if(expenseHouseComboBox.getSelectionModel().getSelectedIndex() == -1 ||
            expenseNameField.getText().isBlank() ||
            expensePriceField.getText().isBlank() ||
            expenseDateField.getValue() == null) {
            System.out.println("Please fill all the fields");
            return;
        }
        houseID = expenseHouseComboBox.getSelectionModel().getSelectedIndex() + 1;
        expenseName = expenseNameField.getText();
        expensePrice = new BigDecimal(expensePriceField.getText());
        expenseDate = Date.valueOf(expenseDateField.getValue());
        System.out.println("Selected house: " + houseID);
        System.out.println("Selected expense: " + expenseName);
        System.out.println("Selected price: " + expensePrice);
        System.out.println("Selected date: " + expenseDate);
        try{
            expensesTable.getItems().add(expenses.enterEntry(houseID, expenseName, expensePrice, expenseDate));
            System.out.println("Successfully entered entry");
        } catch(Exception e){
            System.out.println("Failed to enter");
        }
    }

    public void enterIncome(ActionEvent event){
        int houseID;
        String incomeName;
        BigDecimal incomeAmount;
        Date datePaid;
        Date dateDue;
        if(incomeHouseComboBox.getSelectionModel().getSelectedIndex() == -1 ||
                expenseNameField.getText().isBlank() ||
                expensePriceField.getText().isBlank() ||
                expenseDateField.getValue() == null) {
            System.out.println("Please fill all the fields");
            return;
        }
        houseID = incomeHouseComboBox.getSelectionModel().getSelectedIndex() + 1;
        incomeName = incomeNameField.getText();
        incomeAmount = new BigDecimal(incomeAmountField.getText());
        datePaid = Date.valueOf(incomeDatePaid.getValue());
        dateDue = Date.valueOf(incomeDateDue.getValue());
        System.out.println("Selected house: " + houseID);
        System.out.println("Selected income: " + incomeName);
        System.out.println("Selected amount: " + incomeAmount);
        System.out.println("Selected date paid: " + datePaid);
        System.out.println("Selected date due: " + dateDue);
        try{
            incomeTable.getItems().add(income.enterEntry(houseID, incomeName, incomeAmount, datePaid, dateDue));
            System.out.println("Successfully entered entry");
        } catch(Exception e){
            System.out.println("Failed to enter");
        }
    }

    public void enterHouse(ActionEvent event){
        String streetAddress;
        String city;
        String county;
        String state;
        String zipCode;
        String firstName;
        String lastName;
        String email;
        String phoneNumber;
        if (housesStreetField.getText().isBlank() ||
            housesCityField.getText().isBlank() ||
            housesCountyField.getText().isBlank() ||
            housesStateComboBox.getSelectionModel().getSelectedIndex() == -1 ||
            zipCodeField.getText().isBlank() ||
            housesFirstNameField.getText().isBlank() ||
            housesLastNameField.getText().isBlank() ||
            housesEmailField.getText().isBlank() ||
            housesPhoneField.getText().isBlank()) {
            System.out.println("Please fill all the fields");
            return;
        }
        streetAddress = housesStreetField.getText();
        city = housesCityField.getText();
        county = housesCountyField.getText();
        state = housesStateComboBox.getSelectionModel().getSelectedItem();
        zipCode = zipCodeField.getText();
        firstName = housesFirstNameField.getText();
        lastName = housesLastNameField.getText();
        email = housesEmailField.getText();
        phoneNumber = housesPhoneField.getText();
        System.out.println("Selected street: " + streetAddress);
        System.out.println("Selected city: " + city);
        System.out.println("Selected county: " + county);
        System.out.println("Selected state: " + state);
        System.out.println("Selected zip code: " + zipCode);
        System.out.println("Selected first name: " + firstName);
        System.out.println("Selected last name: " + lastName);
        System.out.println("Selected email: " + email);
        System.out.println("Selected phone number: " + phoneNumber);
        try {
            housesTable.getItems().add(houses.enterEntry(streetAddress, city, county, state, zipCode, firstName, lastName, email, phoneNumber));
            System.out.println("Successfully entered entry");
        } catch(Exception e){
            System.out.println("Failed to enter");
        }
    }
}