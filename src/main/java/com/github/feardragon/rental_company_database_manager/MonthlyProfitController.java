package com.github.feardragon.rental_company_database_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class MonthlyProfitController implements Initializable {

    @FXML
    private ComboBox<String> houseComboBox;
    @FXML
    private TextField yearField;
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private Label blankWarning;
    @FXML
    private Label profits;

    private Expenses expenses;
    private Income income;
    private Houses houses;

    public void setObjects(Expenses expenses,  Income income, Houses houses) {
        this.expenses = expenses;
        this.income = income;
        this.houses = houses;

        try {
            houseComboBox.getItems().add("All");
            houseComboBox.getItems().addAll(houses.getAddresses());
            monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July",
                    "August", "September", "October", "November", "December");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d{0,4}") ? change :  null;
        };
        yearField.setTextFormatter(new TextFormatter<>(filter));
    }

    public void getMonthlyProfit(ActionEvent actionEvent) {
        BigDecimal addedExpenses = BigDecimal.ZERO;
        BigDecimal addedIncome = BigDecimal.ZERO;
        BigDecimal result = BigDecimal.ZERO;
        if(houseComboBox.getSelectionModel().getSelectedIndex() == -1 ||
                monthComboBox.getSelectionModel().getSelectedIndex() == -1 ||
                yearField.getText().isBlank()) {
            blankWarning.setVisible(true);
            return;
        }

        if(houseComboBox.getSelectionModel().getSelectedIndex() == 0) {
            try{
                addedExpenses = expenses.getMonthlyExpense(monthComboBox.getSelectionModel().getSelectedIndex() + 1, Integer.parseInt(yearField.getText()));
                addedIncome = income.getMonthlyIncome(monthComboBox.getSelectionModel().getSelectedIndex() + 1, Integer.parseInt(yearField.getText()));
                result = addedIncome.subtract(addedExpenses);
                profits.setText("Profits: $" + result);
                return;
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            addedExpenses = expenses.getMonthlyExpenseByHouse(monthComboBox.getSelectionModel().getSelectedIndex() + 1, Integer.parseInt(yearField.getText()), houseComboBox.getSelectionModel().getSelectedIndex());
            addedIncome = income.getMonthlyIncomeByHouse(monthComboBox.getSelectionModel().getSelectedIndex() + 1, Integer.parseInt(yearField.getText()), houseComboBox.getSelectionModel().getSelectedIndex());
            result = addedIncome.subtract(addedExpenses);
            blankWarning.setVisible(false);
            profits.setText("Profits: $" + result);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
