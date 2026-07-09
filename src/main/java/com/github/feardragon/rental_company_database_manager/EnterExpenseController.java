package com.github.feardragon.rental_company_database_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EnterExpenseController implements Initializable{

    @FXML
    private ComboBox<String> expenseHouseComboBox;
    @FXML
    private TextField expenseNameField;
    @FXML
    private TextField expensePriceField;
    @FXML
    private DatePicker expenseDateField;
    @FXML
    private Label enterExpenseWarning;
    @FXML
    private Label enterExpenseFailed;
    @FXML
    private Label enterExpenseSuccess;

    private Expenses expenses;
    private Income income;
    private Houses houses;

    public void setExpenses(Expenses expenses){
        this.expenses = expenses;
    }

    public void setIncome(Income income){
        this.income = income;
    }

    public void setHouses(Houses houses){
        this.houses = houses;

        try {
            expenseHouseComboBox.getItems().addAll(houses.getAddresses());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle rb){
        // Formats money field
        UnaryOperator<TextFormatter.Change> moneyFilter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("^\\d*(\\.\\d{0,2})?$") ? change : null;
        };
        setupMoneyField(expensePriceField, moneyFilter);
    }

    public void handleExpensePriceAction() {
        formatMoneyField(expensePriceField);
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

    public void enterExpense(ActionEvent event){
        int houseID;
        String expenseName;
        BigDecimal expensePrice;
        Date expenseDate;
        if(expenseHouseComboBox.getSelectionModel().getSelectedIndex() == -1 ||
                expenseNameField.getText().isBlank() ||
                expensePriceField.getText().isBlank() ||
                expenseDateField.getValue() == null) {
            enterExpenseWarning.setVisible(true);
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
            expenses.enterEntry(houseID, expenseName, expensePrice, expenseDate);
            enterExpenseWarning.setVisible(false);
            enterExpenseFailed.setVisible(false);
            enterExpenseSuccess.setVisible(true);
        } catch(Exception e){
            enterExpenseWarning.setVisible(false);
            enterExpenseSuccess.setVisible(false);
            enterExpenseFailed.setVisible(true);
        }
    }
}
