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

public class YearlyExpensesController implements Initializable {

    @FXML
    private ComboBox<String> houseComboBox;
    @FXML
    private TextField yearField;
    @FXML
    private Label blankWarning;
    @FXML
    private Label results;

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

    public void getYearlyExpenses(ActionEvent actionEvent) {
        BigDecimal result = BigDecimal.ZERO;
        if(houseComboBox.getSelectionModel().getSelectedIndex() == -1 ||
                yearField.getText().isBlank()) {
            blankWarning.setVisible(true);
            return;
        }

        if(houseComboBox.getSelectionModel().getSelectedIndex() == 0) {
            try{
                result = expenses.getYearlyExpense(Integer.parseInt(yearField.getText()));
                results.setText("Expenses: $" + result);
                return;
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        try{
            result = expenses.getYearlyExpenseByHouse(Integer.parseInt(yearField.getText()), houseComboBox.getSelectionModel().getSelectedIndex());
            blankWarning.setVisible(false);
            results.setText("Expenses: $" + result);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
