package com.github.feardragon.rental_company_database_manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class HousesInfoController {

    @FXML
    private Label city;
    @FXML
    private Label county;
    @FXML
    private Label email;
    @FXML
    private Label firstName;
    @FXML
    private Label houseAddress;
    @FXML
    private Label houseID;
    @FXML
    private Label lastName;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label state;
    @FXML
    private Label streetAddress;
    @FXML
    private Label zipCode;
    @FXML
    private TableView<HouseRow> housesTable;

    public void setTable (TableView<HouseRow> housesTable) {
        this.housesTable = housesTable;
        housesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try{
                    houseID.setText("House ID: " + newValue.getHouseID());
                    houseAddress.setText("House Address: " + newValue.getStreetAddress() + ", " +  newValue.getCity() + ", " + newValue.getState() + " " +  newValue.getZipCode());
                    streetAddress.setText("Street Address: " + newValue.getStreetAddress());
                    city.setText("City: " + newValue.getCity());
                    county.setText("County: " + newValue.getCounty());
                    state.setText("State: " + newValue.getState());
                    zipCode.setText("Zip Code: " + newValue.getZipCode());
                    firstName.setText("First Name: " + newValue.getFirstName());
                    lastName.setText("Last Name: " + newValue.getLastName());
                    phoneNumber.setText("Phone Number: " + newValue.getPhoneNumber());
                    email.setText("Email: " + newValue.getEmail());
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
