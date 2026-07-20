package com.github.feardragon.rental_company_database_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class IncomeInfoController {

    @FXML
    private Label county;
    @FXML
    private Label dateDue;
    @FXML
    private Label datePaid;
    @FXML
    private Label email;
    @FXML
    private Label firstName;
    @FXML
    private Label houseAddress;
    @FXML
    private Label incomeAmount;
    @FXML
    private Label incomeID;
    @FXML
    private Label incomeName;
    @FXML
    private Label lastName;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label zipCode;
    @FXML
    private TableView<IncomeRow> incomeTable;

    private Income income;
    private Houses houses;

    public void setObjects(Income income, Houses houses) {
        this.income = income;
        this.houses = houses;
    }

    public void setTable(TableView<IncomeRow> incomeTable) {
        this.incomeTable = incomeTable;
        incomeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try{
                    incomeID.setText("Income ID: " + newValue.getIncomeID());
                    incomeName.setText("Income Name: " + newValue.getIncomeName());
                    incomeAmount.setText("Income Amount: " + newValue.getIncomeAmount());
                    datePaid.setText("Date Paid: " + newValue.getDatePaid());
                    dateDue.setText("Date Due: " + newValue.getDateDue());
                    houseAddress.setText("House Address: " + newValue.getIncomeHouse());
                    county.setText("County: " + houses.getCountyByID(income.getHouseIDByID(newValue.getIncomeID())));
                    zipCode.setText("ZIP Code: " + houses.getZipCodeByID(income.getHouseIDByID(newValue.getIncomeID())));
                    firstName.setText("Tenant First Name: " + houses.getFirstNameByID(income.getHouseIDByID(newValue.getIncomeID())));
                    lastName.setText("Tenant Last Name: " + houses.getLastNameByID(income.getHouseIDByID(newValue.getIncomeID())));
                    phoneNumber.setText("Tenant Phone Number: " + houses.getPhoneByID(income.getHouseIDByID(newValue.getIncomeID())));
                    email.setText("Tenant Email: " + houses.getEmailByID(income.getHouseIDByID(newValue.getIncomeID())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
