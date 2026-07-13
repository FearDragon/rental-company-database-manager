package com.github.feardragon.rental_company_database_manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class IncomeTableController implements Initializable {

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

    public void initialize(URL url, ResourceBundle rb) {
        incomeID.setCellValueFactory(new PropertyValueFactory<IncomeRow, Integer>("incomeID"));
        incomeHouse.setCellValueFactory(new PropertyValueFactory<IncomeRow, String>("incomeHouse"));
        incomeName.setCellValueFactory(new PropertyValueFactory<IncomeRow, String>("incomeName"));
        incomeAmount.setCellValueFactory(new PropertyValueFactory<IncomeRow, BigDecimal>("incomeAmount"));
        datePaid.setCellValueFactory(new PropertyValueFactory<IncomeRow, Date>("datePaid"));
        dateDue.setCellValueFactory(new PropertyValueFactory<IncomeRow, Date>("dateDue"));
    }
}
