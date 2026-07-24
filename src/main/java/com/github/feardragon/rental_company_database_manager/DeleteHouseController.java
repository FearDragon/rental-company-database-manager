package com.github.feardragon.rental_company_database_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class DeleteHouseController {

    @FXML
    private TextField idField;
    @FXML
    private Label deleteHouseFailed;
    @FXML
    private Label deleteHouseSuccess;
    @FXML
    private Label deleteHouseWarning;

    private Houses houses;

    public void setHouses(Houses houses) {
        this.houses = houses;
    }

    @FXML
    public void initialize(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*") ? change :  null;
        };
        idField.setTextFormatter(new TextFormatter<>(filter));
    }

    public void deleteHouse(ActionEvent actionEvent) {
        if (idField.getText().equals("")) {
            deleteHouseFailed.setVisible(false);
            deleteHouseSuccess.setVisible(false);
            deleteHouseWarning.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Enty? \nThis cannot be undone.", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Expense?");
        Optional<ButtonType> input = alert.showAndWait();
        if (input.get() == ButtonType.YES) {
            try{
                int id = Integer.parseInt(idField.getText());
                houses.deleteEntry(id);
                deleteHouseSuccess.setVisible(true);
                deleteHouseFailed.setVisible(false);
                deleteHouseWarning.setVisible(false);
            } catch (Exception e) {
                deleteHouseFailed.setVisible(true);
                deleteHouseWarning.setVisible(false);
                deleteHouseSuccess.setVisible(false);
            }
        }
    }
}
