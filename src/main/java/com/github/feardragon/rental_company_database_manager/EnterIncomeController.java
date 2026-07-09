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

public class EnterIncomeController implements Initializable {

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
    @FXML
    private Label enterIncomeWarning;
    @FXML
    private Label enterIncomeFailed;
    @FXML
    private Label enterIncomeSuccess;

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
            incomeHouseComboBox.getItems().addAll(houses.getAddresses());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        // Formats money field
        UnaryOperator<TextFormatter.Change> moneyFilter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("^\\d*(\\.\\d{0,2})?$") ? change : null;
        };
        setupMoneyField(incomeAmountField, moneyFilter);
    }

    public void handleIncomeAmountAction() {
        formatMoneyField(incomeAmountField);
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

    public void enterIncome(ActionEvent event){
        int houseID;
        String incomeName;
        BigDecimal incomeAmount;
        Date datePaid;
        Date dateDue;
        if(incomeHouseComboBox.getSelectionModel().getSelectedIndex() == -1 ||
                incomeNameField.getText().isBlank() ||
                incomeAmountField.getText().isBlank() ||
                incomeDatePaid.getValue() == null ||
                incomeDateDue.getValue() == null) {
            enterIncomeWarning.setVisible(true);
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
            income.enterEntry(houseID, incomeName, incomeAmount, datePaid, dateDue);
            enterIncomeWarning.setVisible(false);
            enterIncomeFailed.setVisible(false);
            enterIncomeSuccess.setVisible(true);
        } catch(Exception e){
            enterIncomeWarning.setVisible(false);
            enterIncomeSuccess.setVisible(false);
            enterIncomeFailed.setVisible(true);
        }
    }
}
