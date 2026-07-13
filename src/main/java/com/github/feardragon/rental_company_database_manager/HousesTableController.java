package com.github.feardragon.rental_company_database_manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HousesTableController implements Initializable {

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

    public void initialize(URL url, ResourceBundle rb) {
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
}
