package com.github.feardragon.rental_company_database_manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class EnterHouseController implements Initializable {

    ObservableList<String> states = FXCollections.observableArrayList(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
            "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
            "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
            "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
            "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
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
    @FXML
    private Label enterHouseWarning;
    @FXML
    private Label enterHouseFailed;
    @FXML
    private Label enterHouseSuccess;
    @FXML
    private Label enterHouseEmailWarning;

    private Expenses expenses;
    private Income income;
    private Houses houses;

    public void setObjects(Expenses expenses,  Income income, Houses houses) {
        this.expenses = expenses;
        this.income = income;
        this.houses = houses;

        try {
            housesStateComboBox.getItems().addAll(states);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
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
        setupPhoneField(housesPhoneField, phoneFilter);
        zipCodeField.setTextFormatter(new TextFormatter(zipFilter));
        housesEmailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if(!housesEmailField.getText().contains("@") || !housesEmailField.getText().contains(".")) {
                    enterHouseEmailWarning.setVisible(true);
                } else {
                    enterHouseEmailWarning.setVisible(false);
                }
            }
        });
    }

    public void handleHousesPhoneAction() {
        formatPhoneField(housesPhoneField);
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
            enterHouseWarning.setVisible(true);
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
            houses.enterEntry(streetAddress, city, county, state, zipCode, firstName, lastName, email, phoneNumber);
            enterHouseWarning.setVisible(false);
            enterHouseFailed.setVisible(false);
            enterHouseSuccess.setVisible(true);
        } catch(Exception e){
            enterHouseWarning.setVisible(false);
            enterHouseSuccess.setVisible(false);
            enterHouseFailed.setVisible(true);
        }
    }
}
